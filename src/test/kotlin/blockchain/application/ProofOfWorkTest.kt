package blockchain.application

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import org.apache.commons.codec.digest.DigestUtils
import java.lang.Math.random
import kotlin.math.pow

class ProofOfWorkTest : StringSpec({
    "proofOfWork() returns a value that meet the expectations" {
        val seed = random()
        proofOfWork(
            seed = seed,
            f = { a, b -> (a.pow(2) - b.pow(2)).let { DigestUtils.sha256Hex(it.toString()) } },
            isValid = { it.startsWith("0000") }
        ).apply {
            val hash = (this.pow(2) - seed.pow(2)).let { DigestUtils.sha256Hex(it.toString()) }
            hash shouldStartWith "0000"
        }
    }

    "isAValidProof returns true with valid values" {
        isAValidProof(
            curr = 16.0,
            prev = 0.18493704122436794,
            f = { a, b -> (a.pow(2) - b.pow(2)).let { DigestUtils.sha256Hex(it.toString()) } },
            isValid = { it.startsWith("0000") }
        ) shouldBe true
    }

    "isAValidProof returns false with an invalid combination of values" {
        isAValidProof(
            curr = 16.0,
            prev = 0.19,
            f = { a, b -> (a.pow(2) - b.pow(2)).let { DigestUtils.sha256Hex(it.toString()) } },
            isValid = { it.startsWith("0000") }
        ) shouldBe false
    }

})