package blockchain.domain

tailrec fun proofOfWork(
    previousProof: Double,
    newProof: Double = 1.0,
    complexOperation: (Double, Double) -> Double,
    toHash: (Double) -> String,
    isValid: (String) -> Boolean
): Double {
    val hash = toHash(complexOperation(newProof, previousProof))
    return when {
        isValid(hash) -> newProof
        else -> proofOfWork(
            previousProof = previousProof,
            newProof = newProof + 1.0,
            toHash = toHash,
            complexOperation = complexOperation,
            isValid = isValid
        )
    }
}
