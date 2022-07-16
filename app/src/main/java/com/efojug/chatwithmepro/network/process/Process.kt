package com.efojug.chatwithmepro.network.process

import com.efojug.chatwithmepro.network.NetworkClient

abstract class Process(val type: String) {

    protected val request = HashMap<String, String>().apply {
        this["type"] = type
    }

    protected lateinit var onSuccess: (HashMap<String, String>) -> Unit
    protected lateinit var onFail: (failMsg: String) -> Unit

    protected fun sendRequest() = NetworkClient.sendObject(request)

    fun call(
        onSuccess: (HashMap<String, String>) -> Unit = {},
        onFail: (errorMsg: String) -> Unit = {},
        data: HashMap<String, String>,
    ) {
        this.onSuccess = onSuccess
        this.onFail = onFail
        onCalled(data)
    }

    open fun onCalled(data: HashMap<String, String>) {
        request.putAll(data)
        sendRequest()
    }

    open fun process(result: HashMap<String, String>) {
        when (result["state"]!!) {
            "true" -> onSuccess(result)
            "false" -> onFail(result["error_msg"]!!)
        }
    }
}