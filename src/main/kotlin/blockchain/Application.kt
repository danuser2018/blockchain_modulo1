package blockchain

import blockchain.config.configureBlockchain
import blockchain.config.configureRouting
import blockchain.config.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureBlockchain()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
