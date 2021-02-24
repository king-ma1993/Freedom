package com.myl.plugin_core.manager.server

import android.content.IntentFilter
import android.content.pm.PackageInfo
import android.os.Parcel
import android.os.Parcelable

class PluginReceiverInfo(
    var receiverClassName: String,
    var pluginPkgName: String,
    var intentFilters: List<IntentFilter>?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelableList(arrayListOf(), IntentFilter::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.apply {
            writeString(receiverClassName)
            writeString(pluginPkgName)
            writeParcelableList(intentFilters, flags)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PluginReceiverInfo> {
        override fun createFromParcel(parcel: Parcel): PluginReceiverInfo {
            return PluginReceiverInfo(parcel)
        }

        override fun newArray(size: Int): Array<PluginReceiverInfo?> {
            return arrayOfNulls(size)
        }
    }
}