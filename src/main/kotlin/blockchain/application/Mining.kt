package blockchain.application

import blockchain.config.blockchain
import blockchain.config.updateBlockchain
import blockchain.domain.Block
import blockchain.domain.createBlock
import java.time.Instant

suspend fun mineABlock(): Block {
    updateBlockchain {
        createBlock(
            timestamp = Instant.now().toEpochMilli(),
            proof = proofOfWork(seed = last().proof),
            previousHash = last().hash()
        )
    }
    return blockchain.last()
}




