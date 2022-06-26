package com.efojug.chatwithmepro

object ChatDataManager {
    private val list = mutableListOf<ChatData>()
    var userName = ""

    init {
        list.addAll(
            listOf(
                ChatData("Xiaomi", "00-00 00:00:00", "MIUI"),
                ChatData("OPPO", "00-00 00:00:00", "ColorOS"),
                ChatData("MEIZU", "00-00 00:00:00", "Flyme"),
                ChatData("vivo", "00-00 00:00:00", "OriginOS")
            )
        )
    }

    fun add(chatData: ChatData) = list.add(chatData)

    fun getAllChatData() = list
}

data class ChatData(val userName: String, val time: String, val msg: String)
