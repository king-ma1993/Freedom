
package com.myl.gradle_plugin.transform

import javassist.ClassPool
import javassist.CtClass
import javassist.NotFoundException
import javassist.expr.ExprEditor
import javassist.expr.MethodCall

object ReplaceClassName {
    private val mNewNames = mutableSetOf<String>()
    /**
     * MutableMap<defClass, MutableMap<method, MutableSet<useClass>>>
     */
    private val errorResult: MutableMap<String, MutableMap<String, MutableSet<String>>> = mutableMapOf()

    fun resetErrorCount() {
        mNewNames.clear()
        errorResult.clear()
    }

    fun replaceClassName(ctClass: CtClass, oldName: String, newName: String) {
//        println("ReplaceClassName:${ctClass} ${oldName} $newName")
        ctClass.replaceClassName(oldName, newName)
        if(!mNewNames.contains(newName)){
            mNewNames.add(newName)
        }
    }

    fun checkAll(classPool: ClassPool, inputClassNames: List<String>): Map<String, Map<String, Set<String>>> {
        inputClassNames.forEach { inputClassName ->
            val inputClass = classPool[inputClassName]
            if (inputClass.refClasses.any { mNewNames.contains(it) }) {
                mNewNames.forEach { newName ->
                    inputClass.checkMethodExist(classPool[newName])
                }
            }
        }
        return errorResult
    }

    /**
     * 检查ctClass对refClassName引用的方法确实都存在
     * 将传入的ctClass代表的类的方法调用都枚举出来，然后如果调用方法的主人是插件中替换的类，则进行检查是否有这个类
     */
    private fun CtClass.checkMethodExist(refClass: CtClass) {
        val invokeClass = name
        val refClassName = refClass.name
        instrument(object : ExprEditor() {
            override fun edit(m: MethodCall) {
                if (m.className == refClassName) {
                    try {
                        refClass.getMethod(m.methodName, m.signature)
                    } catch (ignored: NotFoundException) {
                        val methodString = "${m.methodName}:${m.signature}"
                        var methodMap = errorResult[refClassName]
                        if (methodMap == null) {
                            methodMap = mutableMapOf()
                            errorResult[refClassName] = methodMap
                        }
                        var useSet = methodMap[methodString]
                        if (useSet == null) {
                            useSet = mutableSetOf()
                            methodMap[methodString] = useSet
                        }
                        useSet.add(invokeClass)
                    }
                }
            }
        })
    }
}