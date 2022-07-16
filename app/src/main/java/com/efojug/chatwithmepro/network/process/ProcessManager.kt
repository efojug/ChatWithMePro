package dove.desu.chatwithme.network.process

object ProcessManager {
    private val map = hashMapOf(
        "register" to RegisterProcess,
        "login" to LoginProcess,
        "msg" to MsgProcess
    )

    fun process(result: HashMap<String, String>) {
        val process = map[result["type"]]
        if (process != null) process.process(result) else return
    }
}