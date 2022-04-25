package blockchain.application

import blockchain.domain.Block
import blockchain.domain.Blockchain

tailrec fun Blockchain.isValid(index: Int = this.size - 1): Boolean {
    val isValid = when {
        this.isEmpty() -> false
        index == 0 -> this[index].isGenesis()
        else -> {
            val curr = this[index]
            val prev = this[index - 1]
            (curr isLinkedTo prev) and (curr hasAProofFor prev)
        }
    }
    return if (isValid && index > 0) this.isValid(index - 1) else isValid
}

private fun Block.isGenesis(): Boolean = index == 0 && proof == 1.0 && previousHash == "0"
private infix fun Block.isLinkedTo(block: Block): Boolean = (this.previousHash == block.hash())
private infix fun Block.hasAProofFor(block: Block): Boolean = isAValidProof(this.proof, block.proof)

