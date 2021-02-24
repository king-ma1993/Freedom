
package com.myl.gradle_plugin.transform

import javassist.CtClass

interface TransformStep {
    fun filter(allInputClass: Set<CtClass>): Set<CtClass>

    fun transform(ctClass: CtClass)
}