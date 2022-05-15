package blockchain.config

import blockchain.domain.Blockchain
import blockchain.domain.emptyBlockchain
import io.ktor.server.application.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Instant

lateinit var blockchain: Blockchain

private val mutex = Mutex()

fun Application.configureBlockchain() = this.launch {
    mutex.withLock { blockchain = emptyBlockchain(Instant.now().toEpochMilli()) }
}

suspend fun updateBlockchain(f: Blockchain.() -> Blockchain) = mutex.withLock {
    blockchain = blockchain.f()
}
