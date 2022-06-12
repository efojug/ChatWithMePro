package com.efojug.chatwithmepro

object ChatDataManager {
    private val list = ArrayList<ChatData>()
    var userName = ""

    init {
        list.addAll(
            listOf(
                ChatData("xiaomi", "114514", "miui14 fa bu"),
                ChatData("apple", "114515", "wo cao ni ma"),
                ChatData(
                    "oppo",
                    "143457",
                    "felpfelpfelpfelflpfelpfelpfelplpfelpfelpfelpfelpfelpfewlp[fewlp[fewlflepflplpelpfelpfelpfelpefpl飞利浦费龙凤配"
                ),
                ChatData("xiaomi", "5973951", "干翻华为"),
                ChatData("huawei", "358109751", "你他妈是来砸场子的吧 傻逼"),
                ChatData("efojug", "111", "Compose On Top")
            )
        )
    }

    fun add(chatData: ChatData) = list.add(chatData)

    fun getAllChatData() = list
}

data class ChatData(val userName: String, val time: String, val msg: String)