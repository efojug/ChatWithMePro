package com.efojug.chatwithmepro.network

import com.google.gson.reflect.TypeToken
import com.efojug.chatwithmepro.MainActivity.toast
import com.google.gson.Gson
import dove.desu.chatwithme.network.process.ProcessManager
import kotlinx.coroutines.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import kotlin.concurrent.thread

val gson = Gson()

object NetworkClient {
    lateinit var socket: Socket
    private lateinit var input: DataInputStream
    private lateinit var output: DataOutputStream

    init {
        thread {
            try {
                socket = Socket("81.69.176.67", 6666)
                input = DataInputStream(socket.getInputStream())
                output = DataOutputStream(socket.getOutputStream())
                start()
            } catch (e: Exception) {
                e.printStackTrace()

                toast("无法连接到服务器")
            }
        }
    }

    private fun start() {
        while (socket.isConnected) {
            val s = input.readUTF()

            val map = gson.fromJson<HashMap<String, String>>(
                s,
                object : TypeToken<HashMap<String, String>>() {}.type
            )

            ProcessManager.process(map)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun sendObject(any: Any) = GlobalScope.launch(Dispatchers.IO) {
        try {
            output.writeUTF(gson.toJson(any))
            output.flush()
        } catch (e: Exception) {
            e.printStackTrace()

            toast("发送数据失败")
        }
    }
}