package blockchain.application

import blockchain.domain.Block
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.apache.commons.codec.digest.DigestUtils

class HashTest: StringSpec({

    val mapper by lazy {
        jacksonObjectMapper()
    }

    "Block's hash should be the result of apply SHA256 over a json version of the block" {
        val block = Block(
            index = 1,
            timestamp = System.currentTimeMillis(),
            proof = 1.0,
            previousHash = "0"
        )

        val expected = mapper.writeValueAsString(block).apply { DigestUtils.sha256Hex(this) }

        block.hash() shouldBe expected
    }

    "ProofOfWork's hash should be the result of apply SHA256 over a string version of the block" {
        val proof = 1.0
        val expected = DigestUtils.sha256Hex(proof.toString())

        proof.hash() shouldBe expected
    }

})