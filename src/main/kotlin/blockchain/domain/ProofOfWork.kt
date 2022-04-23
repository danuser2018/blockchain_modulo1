package blockchain.domain

tailrec fun proofOfWork(
    previousProof: Double,
    newProof: Double = 1.0,
    complexOperation: (Double, Double) -> Double,
    sha256: (Double) -> String,
    isValid: (String) -> Boolean
): Double {
    val hash = sha256(complexOperation(newProof, previousProof))
    return when {
        isValid(hash) -> newProof
        else -> proofOfWork(
            previousProof = previousProof,
            newProof = newProof + 1.0,
            sha256 = sha256,
            complexOperation = complexOperation,
            isValid = isValid
        )
    }
}
