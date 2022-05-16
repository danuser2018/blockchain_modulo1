package blockchain.application

import blockchain.domain.Block
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class HashTest: StringSpec({

    "Block's hash should be the result of apply SHA256 over a base64 of a json version of the block" {
        val block = Block(
            index = 1,
            timestamp = 0L,
            proof = 1.0,
            previousHash = "0"
        )

        val expected = "e8dfb0fe32c957e690a023b013d7b0ef99a738a3cec74e573b366963a5495cb6"

        block.hash() shouldBe expected
    }

    "ProofOfWork's hash should be the result of apply SHA256 over a string version of the block" {
        val proof = 1.0
        val expected = "842f0ffb120a57d8304e656ea205e4d26c8f172d70a4431948523f14049418e6"

        proof.hash() shouldBe expected
    }

})