package blockchain.application

import blockchain.config.blockchain
import blockchain.config.configureBlockchain
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.server.testing.*
import kotlinx.coroutines.launch

class MiningIT : StringSpec({
    "mineABlock adds a new block to the blockchain and returns it" {
        testApplication {
            application {
                configureBlockchain()
                launch {
                    val block = mineABlock()
                    blockchain.size shouldBe 2
                    with(blockchain[0]) {
                        index shouldBe 0
                        proof shouldBe 1.0
                        previousHash shouldBe "0"
                    }
                    with(blockchain[1]) {
                        index shouldBe 1
                        isAValidProof(proof, blockchain[0].proof) shouldBe true
                        previousHash shouldBe blockchain[0].hash()
                    }
                    block shouldBe blockchain[1]
                }
            }
        }
    }
})