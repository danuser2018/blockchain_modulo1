package blockchain.domain

tailrec fun proofOfWork(
    seed: Double,
    candidate: Double = 1.0,
    f: (Double, Double) -> String,
    isValid: (String) -> Boolean
): Double {
    val hash = f(candidate, seed)
    return when {
        isValid(hash) -> candidate
        else -> proofOfWork(
            seed = seed,
            candidate = candidate + 1.0,
            f = f,
            isValid = isValid
        )
    }
}
