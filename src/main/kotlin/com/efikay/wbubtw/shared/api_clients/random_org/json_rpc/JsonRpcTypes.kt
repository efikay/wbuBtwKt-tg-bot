package com.efikay.wbubtw.shared.api_clients.random_org.json_rpc

data class JsonRpcRequest<Params>(
    val method: String,
    val params: Params,
) {
    @Suppress("unused")
    val jsonrpc = "2.0"

    @Suppress("unused")
    val id = 42
}

data class JsonRpcResponse<Result>(
    val jsonrpc: String,
    val id: Int,
    val result: Result,
)