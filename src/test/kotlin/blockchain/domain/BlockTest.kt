package blockchain.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.apache.commons.codec.digest.DigestUtils
import java.lang.Math.random
import kotlin.math.roundToInt

class BlockTest : StringSpec({

    val mapper: ObjectMapper by lazy {
        jacksonObjectMapper()
    }

    "genesisBlock() returns a block that can start a chain" {
        val now = System.currentTimeMillis()
        genesisBlock(now).apply {
            index shouldBe 0
            proof shouldBe 1.0
            timestamp shouldBe now
            previousHash shouldBe "0"
        }
    }

    "hash() returns the hash of the json version of a block" {
        val block = Block(
            index = random().roundToInt(),
            timestamp = System.currentTimeMillis(),
            proof = random(),
            previousHash = "previous_hash"
        )

        val expected = block.let { mapper.writeValueAsString(it) }.let { DigestUtils.sha256Hex(it) }

        block.hash(
            toJson = { mapper.writeValueAsString(it) },
            toHash = { DigestUtils.sha256Hex(it) }
        ) shouldBe expected
    }
})