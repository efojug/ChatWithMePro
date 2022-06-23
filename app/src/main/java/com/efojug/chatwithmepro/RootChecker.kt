package com.efojug.chatwithmepro

import androidx.appcompat.app.AppCompatActivity
import com.efojug.chatwithmepro.ConstantsUtilities.RootUtilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class RootChecker : AppCompatActivity(), CoroutineScope by MainScope() {
    private var rooted: Boolean = false
    private var rootGivenBool: Boolean = false
    private var busyBoxInstalledBool: Boolean = false
    private var isRooted: String = "否"
    private var isGivenRoot: String = "否"
    private var isInstallBusyBox: String = "未安装"


    fun getRootData(): String {
        try {
            rooted = RootUtilities.isRootAvailableOnDevice()
            rootGivenBool = RootUtilities.isRootGivenForDevice()
            busyBoxInstalledBool = RootUtilities.isBusyBoxInstalled()
            if (rooted) isRooted = "是"
            if (rootGivenBool) isGivenRoot = "是"
            if (busyBoxInstalledBool) isInstallBusyBox = "已安装"
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return isRooted + isGivenRoot + isInstallBusyBox
    }
}