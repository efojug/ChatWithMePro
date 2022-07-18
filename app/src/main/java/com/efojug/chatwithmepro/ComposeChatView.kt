package com.efojug.chatwithmepro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.efojug.chatwithmepro.MainActivity.user
import com.efojug.chatwithmepro.MainActivity.username
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private var text by mutableStateOf("")
private var inputBox by mutableStateOf(true)

@Composable
fun ChatRoom(list: MutableList<ChatData>) {
    MainActivity.Vibrate(2)
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(top = 20.dp)
        ) {
            items(list) { item ->
                MsgItem(item)
            }
        }

        Row(
            modifier = Modifier.padding(bottom = 20.dp, start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val focusManager = LocalFocusManager.current
            OutlinedTextField(
                singleLine = false,
                enabled = inputBox,
                maxLines = 5,
                value = text,
                onValueChange = {
                    text = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFF40E0D0),
                    unfocusedIndicatorColor = Color(0xFF1E90FF),
                    errorIndicatorColor = Color.Red,
                    disabledIndicatorColor = Color.Gray
                ), modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(48.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            Button(
                enabled = text.isNotBlank(),
                modifier = Modifier
                    .height(48.dp),
                onClick = {
                    focusManager.clearFocus()
                    MainActivity.sendNotification(text)
                    MainActivity.Vibrate(2)
                    if (user[0]) ChatDataManager.add(
                        ChatData(
                            username,
                            LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("MM-dd HH:mm:ss"))
                                .toString(),
                            text
                        )
                    ) else ChatDataManager.add(
                        ChatData(
                            "Guest",
                            LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("MM-dd HH:mm:ss"))
                                .toString(),
                            text
                        )
                    )
                    text = ""
                },
                elevation = ButtonDefaults.elevation(0.dp),
//                enabled = false,
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (chatData.userName == "efojug") Arrangement.End else Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Top)
            )
            Column {
                Text(text = chatData.userName, fontSize = 12.sp, color = Color.LightGray)
                Surface(
                    modifier = Modifier.padding(
                        start = 4.dp,
                        end = 120.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    ),
                    shape = RoundedCornerShape(6.dp),
                    color = if (chatData.userName == ChatDataManager.userName) Color(0xFF95EC69) else Color(
                        0xFF12B7F5
                    )
                ) {
                    Text(
                        text = chatData.msg,
                        modifier = Modifier.padding(8.dp),
                        color = if (chatData.userName != ChatDataManager.userName) Color.White else Color.Black
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MsgItemPreView() {
    val list = listOf(
        ChatData("Xiaomi", "00-00 00:00:00", "MIUI"),
        ChatData("OPPO", "00-00 00:00:00", "ColorOS"),
        ChatData("MEIZU", "00-00 00:00:00", "Flyme"),
        ChatData("vivo", "00-00 00:00:00", "OriginOS")
    )

    ChatRoom(list = ArrayList(list))
}
