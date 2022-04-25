package blockchain.domain

typealias ProofOfWork = Double

data class Block(
    val index: Int,
    val timestamp: Long,
    val proof: ProofOfWork,
    val previousHash: String
)

fun genesisBlock(timestamp: Long): Block = Block(
    index = 0,
    timestamp = timestamp,
    proof = 1.0,
    previousHash = "0"
)






