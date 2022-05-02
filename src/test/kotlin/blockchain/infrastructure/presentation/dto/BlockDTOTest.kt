package blockchain.infrastructure.presentation.dto

import blockchain.domain.Block
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BlockDTOTest : StringSpec({
    "Given a block, returns a blockDTO" {
        val block = Block(
            index = 1,
            timestamp = 1651084205006,
            proof = 1.0,
            previousHash = "0"
        )

        block.toDTO().apply {
            index shouldBe 1
            timestamp shouldBe "27/4/22 20:30:05"
            proof shouldBe "1"
            previousHash shouldBe "0"
        }
    }
})