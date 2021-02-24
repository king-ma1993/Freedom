@file:Suppress("DEPRECATION")

package com.myl.codegenerate

import android.app.Activity
import android.content.Intent
import android.view.KeyEvent
import android.view.Window
import com.squareup.javapoet.*
import java.io.File
import java.lang.reflect.Method
import javax.lang.model.element.Modifier

class ProxyActivityGenerator {

    companion object {
        const val PREFIX = "Generated"
        const val HostActivityProxy = "${PREFIX}HostActivityProxy"
        val ActivityClass = Activity::class.java
        val activityCallbackMethods =
            getActivityCallbackMethods(ActivityClass)
        val commonJavadoc =
            "由\n" + "{@link com.myl.codegenerate.ProxyActivityGenerator}\n" + "自动生成\n"
        const val ACTIVITY_CONTAINER_PACKAGE = "com.myl.freedom.container"
        //    companion object {
//        const val PREFIX = "Generated"
//        const val HostActivityProxy = "${PREFIX}HostActivityProxy"
//        val ActivityClass = Activity::class.java
//        val activityCallbackMethods = getActivityCallbackMethods(ActivityClass)
//
//    }

        val activityPorxy = defineHostActivityDelegate()

        fun getActivityCallbackMethods(clazz: Class<*>): Set<Method> {
            val callbacks = mutableSetOf<Method>()

            callbacks.addAll(getPluginActivityMethods(clazz))

            val startWithOnMethods = getActivityMethods(
                clazz
            )
                .filter {
                    java.lang.reflect.Modifier.isPublic(it.modifiers) or
                            java.lang.reflect.Modifier.isProtected(it.modifiers)
                }.filter {
                    it.name.startsWith("on")
                }
            callbacks.addAll(startWithOnMethods)

            val callbackInterface = getActivityMethods(clazz)
                .filter {
                    java.lang.reflect.Modifier.isPublic(it.modifiers) or
                            java.lang.reflect.Modifier.isProtected(it.modifiers)
                }.filter {
                    it.hasSameDefineIn(Window.Callback::class.java) or
                            it.hasSameDefineIn(KeyEvent.Callback::class.java)
                }
            callbacks.addAll(callbackInterface)

            return callbacks
        }

        fun getActivityMethods(clazz: Class<*>): List<Method> {
            val allMethods = clazz.methods.toMutableSet()
            allMethods.addAll(clazz.declaredMethods)
            return allMethods
                .filter {
                    it.declaringClass != Object::class.java
                }
        }

        fun Method.hasSameDefineIn(clazz: Class<*>): Boolean {
            return try {
                clazz.getDeclaredMethod(name, *parameterTypes)
                true
            } catch (e: NoSuchMethodException) {
                false
            }
        }

        fun defineHostActivityDelegate() =
            TypeSpec.interfaceBuilder(HostActivityProxy)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc(
                    commonJavadoc
                            + "HostActivity的被委托者接口\n"
                            + "被委托者通过实现这个接口中声明的方法达到替代委托者实现的目的，从而将HostActivity的行为动态化。\n"
                )


        /**
         * 系统和插件都会调用的方法
         */
        fun getPluginActivityMethods(clazz: Class<*>): Set<Method> {
            val set = mutableSetOf<Method>()

            fun addMethod(name: String, vararg args: Class<*>) {
                val method =
                    try {
                        clazz.getDeclaredMethod(name, * args)
                    } catch (e: NoSuchMethodException) {
                        clazz.getMethod(name, * args)
                    }
                set.add(method)
            }

            addMethod("isChangingConfigurations")
            addMethod("finish")
            addMethod(
                "startActivityFromChild",
                Activity::class.java,
                Intent::class.java,
                Int::class.javaPrimitiveType!!
            )
            addMethod("getClassLoader")
            addMethod("getLayoutInflater")
            addMethod("getResources")
            addMethod("recreate")
            addMethod("getCallingActivity")

            return set
        }

        fun generate(outputDir: File) {
            val runtimeOutput = File(outputDir, "runtime")
            val activityContainerOutput = File(outputDir, "activity_container")
            activityContainerOutput.mkdirs()
            runtimeOutput.mkdirs()
            addMethods()
            writeOutJavaFiles(activityContainerOutput)
        }


        fun writeOutJavaFiles(activityContainerOutput: File) {
            JavaFile.builder(ACTIVITY_CONTAINER_PACKAGE, activityPorxy.build())
                .build().writeTo(activityContainerOutput)
//        JavaFile.builder(ACTIVITY_CONTAINER_PACKAGE, activityDelegator.build())
//            .build().writeTo(activityContainerOutput)
//        JavaFile.builder(ACTIVITY_CONTAINER_PACKAGE, pluginContainerActivity.build())
//            .build().writeTo(activityContainerOutput)
//        JavaFile.builder(RUNTIME_PACKAGE, pluginActivity.build())
//            .build().writeTo(runtimeOutput)
//        JavaFile.builder(DELEGATE_PACKAGE, shadowActivityDelegate.build())
//            .build().writeTo(loaderOutput)
        }


        fun addMethods() {
            //将系统会调用的方法都定义出来，供转调之用。
            activityPorxy.addMethods(activityCallbackMethods.map { it.toInterfaceMethodSpec() })
        }

        fun Method.toInterfaceMethodSpec(prefix: String = ""): MethodSpec {
            val builder = toMethodSpecBuilder(prefix)
            builder.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
            return builder.build()
        }

        fun Method.toMethodSpecBuilder(prefix: String = ""): MethodSpec.Builder {
            val methodName = if (prefix.isEmpty()) name else prefix + name.capitalize()
            val builder = MethodSpec.methodBuilder(methodName)
            parameters.forEach {
                builder.addParameter(
                    ParameterSpec.builder(it.parameterizedType, it.name).build()
                )
            }
            builder.addExceptions(
                exceptionTypes.map {
                    TypeName.get(it)
                }
            )
            builder.addTypeVariables(typeParameters.map {
                TypeVariableName.get(it)
            })
            builder.returns(genericReturnType)
            return builder
        }
    }


}