package com.efojug.chatwithmepro

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.efojug.chatwithmepro.ConstantsUtilities.RootUtilities
import kotlinx.coroutines.*

class RootChecker : AppCompatActivity(), CoroutineScope by MainScope() {
    private var rooted: Boolean = false
    private var rootGivenBool: Boolean = false
    private var busyBoxInstalledBool: Boolean = false

    fun getRootData(view: View) {
        launch(Dispatchers.Default) {
            try {
                rooted = RootUtilities.isRootAvailableOnDevice()
                rootGivenBool = RootUtilities.isRootGivenForDevice()
                busyBoxInstalledBool = RootUtilities.isBusyBoxInstalled()
                withContext(Dispatchers.Main) {
                    if (rooted) {
                        view.findViewById<TextView>(R.id.ROOT).text = "是"
                        if (rootGivenBool) view.findViewById<TextView>(R.id.giveROOT).text = "是"
                        else view.findViewById<TextView>(R.id.giveROOT).text = "否"
                        if (busyBoxInstalledBool) view.findViewById<TextView>(R.id.BusyBox).text = "已安装"
                        else view.findViewById<TextView>(R.id.BusyBox).text = "未安装"
                    } else view.findViewById<TextView>(R.id.ROOT).text = "否"
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}