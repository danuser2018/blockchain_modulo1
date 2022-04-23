package blockchain.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BlockchainTest : StringSpec({
    "genesisBlock() returns a block that can start a chain" {
        val now = System.currentTimeMillis()
        genesisBlock(now).apply {
            index shouldBe 0
            proof shouldBe 1
            timestamp shouldBe now
            previousHash shouldBe "0"
        }
    }

    "emptyBlockChain() returns a new Blockchain with only the genesis block" {
        val now = System.currentTimeMillis()
        emptyBlockchain(now).apply {
            size shouldBe 1
            this[0] shouldBe genesisBlock(now)
        }
    }

    "createBlock() returns a new Blockchain with a new element" {
        val now = System.currentTimeMillis()
        emptyBlockchain(System.currentTimeMillis()).createBlock(
            timestamp = now,
            proof = 2,
            previousHash = "previous_hash"
        ).apply {
            size shouldBe 2
            this[1].index shouldBe 1
            this[1].timestamp shouldBe now
            this[1].proof shouldBe 2
            this[1].previousHash shouldBe "previous_hash"
        }
    }
})