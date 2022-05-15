package blockchain.application

import blockchain.domain.Block
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class HashTest: StringSpec({

    "Block's hash should be the result of apply SHA256 over a json version of the block" {
        val block = Block(
            index = 1,
            timestamp = 0L,
            proof = 1.0,
            previousHash = "0"
        )

        val expected = "699a72c25cb594be0823ff99681db704cb48b78b93a8ff6e17596eaa01a0fc66"

        block.hash() shouldBe expected
    }

    "ProofOfWork's hash should be the result of apply SHA256 over a string version of the block" {
        val proof = 1.0
        val expected = "d0ff5974b6aa52cf562bea5921840c032a860a91a3512f7fe8f768f6bbe005f6"

        proof.hash() shouldBe expected
    }

})