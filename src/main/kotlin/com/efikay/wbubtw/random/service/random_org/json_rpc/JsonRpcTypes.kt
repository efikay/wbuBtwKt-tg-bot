package com.efikay.wbubtw.random.service.random_org.json_rpc

data class JsonRpcRequest<Params>(
    val method: String,
    val params: Params,
) {
    val jsonrpc = "2.0"
    val id = 42
}

data class JsonRpcResponse<Result>(
    val jsonrpc: String,
    val id: Int,
    val result: Result,
)