
package com.myl.gradle_plugin.transform

import com.myl.gradle_plugin.transform.replace.SpecificTransform
import javassist.CtClass

open class SimpleRenameTransform(private val fromToMap: Map<String, String>) : SpecificTransform() {
    final override fun setup(allInputClass: Set<CtClass>) {
        newStep(object : TransformStep {
            override fun filter(allInputClass: Set<CtClass>) = allInputClass

            override fun transform(ctClass: CtClass) {

                var isSkip = false
                val whiteList = getWhiteList()
                if (whiteList?.contains(ctClass.name) == true) {
                    isSkip = true
                }
                println("SimpleRenameTransform ctClass==${ctClass.name} isSkip:$isSkip")

                fromToMap.forEach {
                    val notReplace = isSkip
                    if (!notReplace) {
                        ReplaceClassName.replaceClassName(ctClass, it.key, it.value)
                    }
                }
            }
        })
    }
}