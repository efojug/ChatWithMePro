package com.efojug.chatwithmepro.network.data

data class MsgData(
    val senderId: Int,
    val senderName: String,
    val time: Long,
    val msg: String
)

data class User(
    val userId: Int,
    val userName: String,
    val password: String
)
