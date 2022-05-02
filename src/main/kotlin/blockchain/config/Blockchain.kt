package blockchain.config

import blockchain.domain.Blockchain
import blockchain.domain.emptyBlockchain
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Instant

var blockchain: Blockchain = emptyBlockchain(Instant.now().toEpochMilli())

private val mutex = Mutex()
suspend fun updateBlockchain(f: Blockchain.() -> Blockchain) = mutex.withLock { blockchain = blockchain.f() }