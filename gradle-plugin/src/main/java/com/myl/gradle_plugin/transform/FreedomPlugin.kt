package com.myl.gradle_plugin.transform

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import java.io.File
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

class FreedomPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("FreedomPlugin project.name==" + project.name)

        val plugin = project.plugins.getPlugin(AppPlugin::class.java)
        val sdkDirectory = plugin.baseExtension.sdkDirectory
//        val androidExtension = project.extensions.getByName("android") as BaseExtension
//        println("androidExtension compileSdkVersion:${plugin.baseExtension.compileSdkVersion}")
//        val androidJarPath = "platforms/${androidExtension.compileSdkVersion}/android.jar"
        val androidJarPath = "platforms/${plugin.baseExtension.compileSdkVersion}/android.jar"
        val androidJar = File(sdkDirectory, androidJarPath)

        val contextClassLoader = Thread.currentThread().contextClassLoader
        val classPoolBuilder = AndroidClassPoolBuilder(project, contextClassLoader, androidJar)
        plugin.baseExtension.registerTransform(FreedomTransform(project, classPoolBuilder))
    }

    private val AppPlugin.baseExtension: BaseExtension
        get() {

            return if (com.android.builder.model.Version.ANDROID_GRADLE_PLUGIN_VERSION == "3.0.0") {
                val method = BasePlugin::class.declaredFunctions.first { it.name == "getExtension" }
                method.isAccessible = true
                method.call(this) as BaseExtension
            } else {
                extension
            }
        }
}