package blockchain.domain

typealias Blockchain = List<Block>

fun emptyBlockchain(timestamp: Long) = listOf(genesisBlock(timestamp))

fun Blockchain.createBlock(timestamp: Long, proof: ProofOfWork, previousHash: String): Blockchain =
    toMutableList() + Block(
        index = size,
        timestamp = timestamp,
        proof = proof,
        previousHash = previousHash
    )




