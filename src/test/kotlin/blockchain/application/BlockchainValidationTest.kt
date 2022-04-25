package blockchain.application

import blockchain.domain.Block
import blockchain.domain.createBlock
import blockchain.domain.emptyBlockchain
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BlockchainValidationTest : StringSpec({
    "isValid() returns false for an empty list" {
        val chain = emptyList<Block>()
        chain.isValid() shouldBe false
    }

    "isValid() returns true for an empty chain (only has a genesis block)" {
        val chain = emptyBlockchain(System.currentTimeMillis())
        chain.isValid() shouldBe true
    }

    "isValid() returns false for an empty chain where the only element is not a genesis block" {
        val chain = emptyList<Block>().createBlock(System.currentTimeMillis(), 2.0, "a hash")
        chain.isValid() shouldBe false
    }

    "isValid() returns true for a valid chain" {
        val chain = with(emptyBlockchain(System.currentTimeMillis())) {
            this.createBlock(
                timestamp = System.currentTimeMillis(),
                proof = 38561.0,
                previousHash = this[0].hash()
            )
        }
        chain.isValid() shouldBe true
    }

    "isValid() returns false if the link is broken" {
        val chain = with(emptyBlockchain(System.currentTimeMillis())) {
            this.createBlock(
                timestamp = System.currentTimeMillis(),
                proof = 38561.0,
                previousHash = "previous_hash"
            )
        }
        chain.isValid() shouldBe false
    }

    "isValid() returns false if there is not a valid proof of work" {
        val chain = with(emptyBlockchain(System.currentTimeMillis())) {
            this.createBlock(
                timestamp = System.currentTimeMillis(),
                proof = 5.0,
                previousHash = this[0].hash()
            )
        }
        chain.isValid() shouldBe false
    }


})