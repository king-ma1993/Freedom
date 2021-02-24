package com.myl.plugin_core.manager.server

import android.content.pm.PackageInfo
import android.os.Parcel
import android.os.Parcelable

class PluginServerInfo(
    var packageInfo: PackageInfo,
    var localPluginPath: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(PackageInfo::class.java.classLoader)!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.apply {
            writeParcelable(packageInfo, flags)
            writeString(localPluginPath)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PluginServerInfo> {
        override fun createFromParcel(parcel: Parcel): PluginServerInfo {
            return PluginServerInfo(parcel)
        }

        override fun newArray(size: Int): Array<PluginServerInfo?> {
            return arrayOfNulls(size)
        }
    }

    var pluginVersion: Int = 0
    var packageName: String = ""
    var odexDirPath: String? = null
    var libSoPath: String? = null
    var nativeLibsDir: String? = null

    init {
        pluginVersion = packageInfo.versionCode
        packageName = packageInfo.packageName
    }
}