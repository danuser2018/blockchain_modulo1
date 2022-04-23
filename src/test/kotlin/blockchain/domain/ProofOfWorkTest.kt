package blockchain.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldStartWith
import org.apache.commons.codec.digest.DigestUtils
import java.lang.Math.random
import kotlin.math.pow

class ProofOfWorkTest : StringSpec({
    "proofOfWork() returns a value that meet the expectations" {
        val previousProof = random()
        proofOfWork(
            previousProof = previousProof,
            complexOperation = { a, b -> a.pow(2) - b.pow(2) },
            toHash = { DigestUtils.sha256Hex(it.toString()) },
            isValid = { it.startsWith("0000") }
        ).apply {
            val hash = (this.pow(2) - previousProof.pow(2)).let { DigestUtils.sha256Hex(it.toString()) }
            hash shouldStartWith "0000"
        }
    }
})