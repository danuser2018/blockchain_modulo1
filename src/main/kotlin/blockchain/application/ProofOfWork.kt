package blockchain.application

import blockchain.domain.ProofOfWork
import kotlin.math.pow

tailrec fun proofOfWork(
    seed: ProofOfWork,
    candidate: ProofOfWork = 1.0,
    f: (ProofOfWork, ProofOfWork) -> String = ::powerOf2Diff,
    isValid: (String) -> Boolean = ::hashWith4Zeros
): ProofOfWork {
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

fun isAValidProof(
    curr: ProofOfWork,
    prev: ProofOfWork,
    f: (ProofOfWork, ProofOfWork) -> String = ::powerOf2Diff,
    isValid: (String) -> Boolean = ::hashWith4Zeros
): Boolean = isValid(f(curr, prev))

private fun powerOf2Diff(a: Double, b: Double): String = (a.pow(2) - b.pow(2)).hash()
private fun hashWith4Zeros(hash: String): Boolean = hash.startsWith("0000")
