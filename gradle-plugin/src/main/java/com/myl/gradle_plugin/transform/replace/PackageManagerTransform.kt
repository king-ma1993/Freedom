package com.myl.gradle_plugin.transform.replace

import com.myl.gradle_plugin.transform.CodeConverterExtension
import com.myl.gradle_plugin.transform.TransformStep
import javassist.CtClass
import javassist.CtMethod
import javassist.CtNewMethod
import javassist.Modifier
import java.util.ArrayList

class PackageManagerTransform : SpecificTransform() {

    companion object {
        const val PackageManagerClassname = "android.content.pm.PackageManager"
        const val RedirectPackageManagerClassname = "com.myl.androidapkplugin.pluginlib.core.PackageManagerRedirect"
        const val SUFFIX = "_plugin"
    }


    private val packageMethodArray = arrayOf(
        "getApplicationInfo",
        "getActivityInfo",
        "getPackageInfo",
        "resolveContentProvider"
    )

    override fun setup(allInputClass: Set<CtClass>) {
        setupPackageManagerTransform(
            packageMethodArray
        )
    }

    private fun setupPackageManagerTransform(targetMethodName: Array<String>) {
        val targetMethods = getTargetMethods(arrayOf(PackageManagerClassname), targetMethodName)
        targetMethods.forEach { targetMethod ->
            newStep(object : TransformStep {
                override fun filter(allInputClass: Set<CtClass>) =
                    allCanRecompileAppClass(
                        allInputClass,
                        listOf(PackageManagerClassname)
                    ).filter {
                        matchMethodCallInClass(targetMethod, it)
                    }.toSet()

                override fun transform(ctClass: CtClass) {
                    try {
                        val targetClass = mClassPool[PackageManagerClassname]
                        val parameterTypes: Array<CtClass> =
                            Array(targetMethod.parameterTypes.size + 1) { index ->
                                if (index == 0) {
                                    targetClass
                                } else {
                                    targetMethod.parameterTypes[index - 1]
                                }
                            }
                        val newMethod = CtNewMethod.make(
                            Modifier.PUBLIC or Modifier.STATIC,
                            targetMethod.returnType,
                            targetMethod.name + SUFFIX,
                            parameterTypes,
                            targetMethod.exceptionTypes,
                            null,
                            ctClass
                        )
                        val newBodyBuilder = StringBuilder()
                        newBodyBuilder
                            .append("return ")
                            .append(RedirectPackageManagerClassname)
                            .append(".")
                            .append(targetMethod.methodInfo.name)
                            .append("(")
                            .append(ctClass.name)
                            .append(".class.getClassLoader(),")
                        //下面放弃第0个和第1个参数，第0个是this，
                        //第1个是redirectMethodCallToStaticMethodCall时原本被调用的PackageManager对象。
                        for (i in 2..newMethod.parameterTypes.size) {
                            if (i > 2) {
                                newBodyBuilder.append(',')
                            }
                            newBodyBuilder.append("\$${i}")
                        }
                        newBodyBuilder.append(");")

                        newMethod.setBody(newBodyBuilder.toString())
                        ctClass.addMethod(newMethod)
                        val codeConverter = CodeConverterExtension()
                        codeConverter.redirectMethodCallToStaticMethodCall(targetMethod, newMethod)
                        ctClass.instrument(codeConverter)
                    } catch (e: Exception) {
                        System.err.println("处理" + ctClass.name + "时出错:" + e)
                        throw e
                    }
                }
            })
        }
    }

    /**
     * 查找目标class对象的目标method
     */
    private fun getTargetMethods(
        targetClassNames: Array<String>,
        targetMethodName: Array<String>
    ): List<CtMethod> {
        val methodTargets = ArrayList<CtMethod>()
        for (targetClassName in targetClassNames) {
            val methods = mClassPool[targetClassName].methods
            methodTargets.addAll(methods.filter { targetMethodName.contains(it.name) })
        }
        return methodTargets
    }
}