package com.myl.gradle_plugin.transform

import com.android.build.api.transform.TransformInvocation
import org.gradle.api.Project

class FreedomTransform(
    project: Project,
    classPoolBuilder: ClassPoolBuilder
): AbstractTransform(project, classPoolBuilder) {

    private lateinit var _mTransformManager: FreedomTransformManager

    override val mTransformManager: AbstractTransformManager
        get() = _mTransformManager

    override fun beforeTransform(invocation: TransformInvocation) {
        super.beforeTransform(invocation)
        _mTransformManager = FreedomTransformManager(mCtClassInputMap, classPool)
    }

    override fun getName(): String = "FreedomTransform"

}