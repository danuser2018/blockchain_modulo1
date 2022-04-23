package blockchain.domain

typealias Blockchain = List<Block>

data class Block(
    val index: Int,
    val timestamp: Long,
    val proof: Double,
    val previousHash: String
)

fun emptyBlockchain(timestamp: Long) = listOf(genesisBlock(timestamp))

fun genesisBlock(timestamp: Long) = Block(
    index = 0,
    timestamp = timestamp,
    proof = 1.0,
    previousHash = "0"
)

fun Blockchain.createBlock(timestamp: Long, proof: Double, previousHash: String): Blockchain =
    Block(
        index = size,
        timestamp = timestamp,
        proof = proof,
        previousHash = previousHash
    ).let { newBlock ->
        toMutableList() + newBlock
    }

