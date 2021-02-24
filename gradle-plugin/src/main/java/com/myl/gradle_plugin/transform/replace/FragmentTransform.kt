

package com.myl.gradle_plugin.transform.replace

import com.myl.gradle_plugin.transform.*
import com.myl.gradle_plugin.transform.replace.Constants.baseActivityDelegate
import com.myl.gradle_plugin.transform.replace.Constants.basePluginActivity
import com.myl.gradle_plugin.transform.replace.Constants.containerFragment
import com.myl.gradle_plugin.transform.replace.Constants.containerdialogfragment
import com.myl.gradle_plugin.transform.replace.Constants.dialogFragmentClassname
import com.myl.gradle_plugin.transform.replace.Constants.fragmentClassname
import com.myl.gradle_plugin.transform.replace.Constants.fragmentManagerClassname
import com.myl.gradle_plugin.transform.replace.Constants.fragmentTransactionClassname
import com.myl.gradle_plugin.transform.replace.Constants.iContainerdialogfragment
import com.myl.gradle_plugin.transform.replace.Constants.pluginDialogFragment
import com.myl.gradle_plugin.transform.replace.Constants.replace_FragmentTransactionName
import com.myl.gradle_plugin.transform.replace.Constants.replace_activityName
import com.myl.gradle_plugin.transform.replace.Constants.replace_fragmentManagerName
import com.myl.gradle_plugin.transform.replace.Constants.replace_fragmentName
import javassist.CtClass
import java.io.File

class FragmentTransform(val mCtClassInputMap: Map<CtClass, InputClass>) : SpecificTransform() {

    val mAppFragments: MutableSet<CtClass> = mutableSetOf()
    val mAppDialogFragments: MutableSet<CtClass> = mutableSetOf()
    private lateinit var fragmentsName: List<String>
    private fun String.appendFragmentAppendSuffix() = this + "_"
    private val containerFragmentNameList = arrayListOf<String>()

    /**
     * 当前Transform的App中的Fragment的父类，不在当前Transform的App中，但它是Fragment，记录在这个集合。
     */
    val mRuntimeSuperclassFragments: MutableSet<CtClass> = mutableSetOf()

    init {
        containerFragmentNameList.add(containerFragment)
        containerFragmentNameList.add(containerdialogfragment)
    }

    override fun setup(allInputClass: Set<CtClass>) {
        //收集哪些当前Transform的App类是Fragment
        newStep(object : TransformStep {
            override fun filter(allInputClass: Set<CtClass>): Set<CtClass> = allInputClass

            override fun transform(ctClass: CtClass) {
                if (containerFragmentNameList.contains(ctClass.name)) {
                    return
                }
                if (ctClass.isDialogFragment()) {
                    mAppDialogFragments.add(ctClass)
                } else if (ctClass.isFragment()) {
                    mAppFragments.add(ctClass)
                }
            }
        })

        //收集不在当前Transform的App中的类，但它是Fragment.只关心App中的Fragment的父类即可。
        newStep(object : TransformStep {
            override fun filter(allInputClass: Set<CtClass>): Set<CtClass> =
                listOf<Set<CtClass>>(mAppDialogFragments, mAppFragments).flatten().toSet()

            override fun transform(ctClass: CtClass) {
                val superclass = ctClass.superclass
                if (superclass !in mAppDialogFragments
                    && superclass !in mAppFragments
                    && superclass.isFragment()
                    && superclass.name != fragmentClassname
                    && superclass.name != dialogFragmentClassname
                ) {
                    mRuntimeSuperclassFragments.add(superclass)
                }
            }
        })

        newStep(object : TransformStep {
            override fun filter(allInputClass: Set<CtClass>) = allInputClass

            override fun transform(ctClass: CtClass) {

                var isSkip = false
                val whiteList = getWhiteList()
                if (whiteList?.contains(ctClass.name) == true) {
                    isSkip = true
                }
                println("FragmentTransform ctClass==${ctClass.name} isSkip:$isSkip")

                replaceMap.forEach {
                    val notReplace = isSkip
                    if (!notReplace) {
                        ReplaceClassName.replaceClassName(ctClass, it.key, it.value)
                    }
                }
            }
        })


        //将App中所有Fragment名字都加上后缀
        newStep(object : TransformStep {
            override fun filter(allInputClass: Set<CtClass>): Set<CtClass> {
                val flattenList = listOf(
                    mAppFragments,
                    mAppDialogFragments,
                    mRuntimeSuperclassFragments
                ).flatten()

                fragmentsName = flattenList.flatMap { listOf(it.name) }

                return flattenList.toSet()
            }

            override fun transform(ctClass: CtClass) {
                val fragmentName = ctClass.name
                ReplaceClassName.replaceClassName(
                    ctClass,
                    fragmentName,
                    fragmentName.appendFragmentAppendSuffix()
                )
            }
        })

        //将App中所有对Fragment的引用也都改为加上后缀名的
        newStep(object : TransformStep {
            override fun filter(allInputClass: Set<CtClass>): Set<CtClass> {
                return allInputClass
            }

            override fun transform(ctClass: CtClass) {
                fragmentsName.forEach { fragmentName ->
                    ReplaceClassName.replaceClassName(
                        ctClass,
                        fragmentName,
                        fragmentName.appendFragmentAppendSuffix()
                    )
                }
            }

        })

        newStep(MakeContainerStep(mAppFragments, mClassPool[containerFragment]))
        newStep(MakeContainerStep(mAppDialogFragments, mClassPool[containerdialogfragment]))
    }


    inner class MakeContainerStep(
        private val inputClass: Set<CtClass>,
        private val container: CtClass
    ) : TransformStep {
        override fun filter(allInputClass: Set<CtClass>) = inputClass

        override fun transform(ctClass: CtClass) {
            val originalFragmentName = ctClass.name.removeFragmentAppendSuffix()
            val newContainerFragmentCtClass = mClassPool.makeClass(originalFragmentName, container)

            val outputControl = mCtClassInputMap[ctClass]!!
            var ctClassOriginOutputFile: File? = null
            var ctClassOriginOutputEntryName: String? = null
            when (outputControl) {
                is DirInputClass -> {
                    ctClassOriginOutputFile = outputControl.getOutput(originalFragmentName)
                }
                is JarInputClass -> {
                    ctClassOriginOutputEntryName = outputControl.getOutput(originalFragmentName)
                }
            }

            outputControl.renameOutput(originalFragmentName, ctClass.name)

            when (outputControl) {
                is DirInputClass -> {
                    outputControl.addOutput(
                        newContainerFragmentCtClass.name,
                        ctClassOriginOutputFile!!
                    )
                }
                is JarInputClass -> {
                    outputControl.addOutput(
                        newContainerFragmentCtClass.name,
                        ctClassOriginOutputEntryName!!
                    )
                }
            }
        }

        private fun String.removeFragmentAppendSuffix() = this.substring(0, this.length - 1)
    }

    val replaceMap = mapOf(
        fragmentManagerClassname
                to replace_fragmentManagerName,
        fragmentClassname to replace_fragmentName,
        fragmentTransactionClassname to replace_FragmentTransactionName,
        dialogFragmentClassname to pluginDialogFragment
    )

    override fun getWhiteList(): List<String>? {
        val appWhiteList = arrayListOf<String>()
        super.getWhiteList()?.apply {
            appWhiteList.addAll(this)
        }
        appWhiteList.add(replace_activityName)
        appWhiteList.add(replace_fragmentManagerName)
        appWhiteList.add(pluginDialogFragment)
        appWhiteList.add(replace_FragmentTransactionName)
        appWhiteList.add(replace_fragmentName)
        appWhiteList.add(containerFragment)
        appWhiteList.add(iContainerdialogfragment)
        appWhiteList.add(containerdialogfragment)
        appWhiteList.add(baseActivityDelegate)
        appWhiteList.add(basePluginActivity)
        return appWhiteList
    }

    private fun CtClass.isFragment(): Boolean = isClassOf(fragmentClassname)

    private fun CtClass.isDialogFragment(): Boolean = isClassOf(dialogFragmentClassname)

}
