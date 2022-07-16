package dove.desu.chatwithme.network.process

import com.efojug.chatwithmepro.ChatData
import com.efojug.chatwithmepro.ChatDataManager
import com.efojug.chatwithmepro.network.process.Process
import com.efojug.chatwithmepro.network.data.MsgData
import com.efojug.chatwithmepro.network.gson

object MsgProcess: Process("msg") {
    override fun onCalled(data: HashMap<String, String>) {
        request.putAll(data)

        sendRequest()
    }

    override fun process(result: HashMap<String, String>) {
        val data = gson.fromJson(result["data"].toString(), MsgData::class.java)

        ChatDataManager.add(ChatData(data.senderName, data.time.toString(), data.msg))
    }
}