package blockchain.infrastructure.presentation.dto

import blockchain.application.hash
import blockchain.domain.Block
import java.text.DateFormat

data class BlockDTO(
    val index: Int,
    val timestamp: String,
    val proof: String,
    val previousHash: String,
    val hash: String
)

fun Block.toDTO() = BlockDTO(
    index = index,
    timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(timestamp),
    proof = proof.toInt().toString(),
    previousHash = previousHash,
    hash = this.hash()
)