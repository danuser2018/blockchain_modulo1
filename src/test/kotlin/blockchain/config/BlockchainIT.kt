package blockchain.config

import blockchain.domain.emptyBlockchain
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.server.testing.*
import java.time.Instant

class BlockchainIT: StringSpec ({
    "When application starts the blockchain is empty" {
        testApplication {
            blockchain = emptyBlockchain(Instant.now().toEpochMilli())
            blockchain.size shouldBe 1
            with(blockchain[0]) {
                index shouldBe 0
                proof shouldBe 1.0
                previousHash shouldBe "0"
            }
        }
    }

    "updateBlockChain executes the function passed as parameter and publish the new blockchain obtained" {
        testApplication {
            blockchain = emptyBlockchain(Instant.now().toEpochMilli())
            runBlocking {
                updateBlockchain {
                    map { it.copy(index = 1, proof = 2.0, previousHash = "1") }
                }
            }
            blockchain.size shouldBe 1
            with(blockchain[0]) {
                index shouldBe 1
                proof shouldBe 2.0
                previousHash shouldBe "1"
            }
        }
    }
})