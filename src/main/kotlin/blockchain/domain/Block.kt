package blockchain.domain

data class Block(
    val index: Int,
    val timestamp: Long,
    val proof: Double,
    val previousHash: String
)

fun genesisBlock(timestamp: Long): Block = Block(
    index = 0,
    timestamp = timestamp,
    proof = 1.0,
    previousHash = "0"
)

fun Block.hash(toJson: (Block) -> String, toHash: (String) -> String): String = toHash(toJson(this))