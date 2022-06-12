package com.efojug.chatwithmepro

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatRoom(list: ArrayList<ChatData>) {
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            itemsIndexed(list) { index: Int, item: ChatData ->
                MsgItem(item)
            }
        }

        var text by remember {
            mutableStateOf("")
        }

        var height by remember {
            mutableStateOf(0)
        }

        val heightDp by animateDpAsState(targetValue = if (height == 0) 40.dp else (height + 40).dp)

        Row(
            modifier = Modifier.padding(bottom = 20.dp, start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var b = 12
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    if (it.length > b) {
                        b += 12
                        height += 20
                    } else if (it.length < 12) {
                        b = 12
                        height = 0
                    } else if (b > 12) {
                        b = 12
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFF90EE90),
                    unfocusedIndicatorColor = Color(0xFF1E90FF),
                    errorIndicatorColor = Color.Red,
                    disabledIndicatorColor = Color.Gray
                ), modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(heightDp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF06C361))
            ) {
                Text(text = "发送", color = Color.White)
            }
        }
    }
}

@Composable
fun MsgItem(chatData: ChatData) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = chatData.time,
            color = Color.Gray,
            fontSize = 8.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
    Surface {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Top)
            )
            Column {
                Text(text = "${chatData.userName} :", fontSize = 12.sp, color = Color.LightGray)
                Surface(
                    modifier = Modifier
                        .padding(4.dp), shape = RoundedCornerShape(6.dp),
                    color =
                    if (chatData.userName == ChatDataManager.userName) Color(0xFF95EC69) else Color.White
                ) {
                    Text(text = chatData.msg, modifier = Modifier.padding(3.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MsgItemPreView() {
    val list = listOf(
        ChatData("xiaomi", "114514", "miui14 fa bu"),
        ChatData("apple", "114515", "wo cao ni ma"),
        ChatData(
            "oppo",
            "143457",
            "fhsajasjk刷机想吃饺子选从欧艾斯糊sicxnm,asdhfe配"
        ),
        ChatData("xiaomi", "5973951", "干翻华为"),
        ChatData("huawei", "358109751", "你他妈是来砸场子的吧 傻逼"),
        ChatData("efojug", "111", "Compose On Top")
    )

    ChatRoom(list = ArrayList(list))
}