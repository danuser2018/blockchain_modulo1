package blockchain.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe


class BlockTest : StringSpec({
    "genesisBlock() returns a block that can start a chain" {
        val now = System.currentTimeMillis()
        genesisBlock(now).apply {
            index shouldBe 0
            proof shouldBe 1.0
            timestamp shouldBe now
            previousHash shouldBe "0"
        }
    }
})