package blockchain.application

import blockchain.domain.Block
import blockchain.domain.ProofOfWork
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.codec.digest.DigestUtils

private val mapper by lazy {
    jacksonObjectMapper()
}

fun Block.hash(): String = mapper.writeValueAsString(this).run { DigestUtils.sha256Hex(this) }
fun ProofOfWork.hash(): String = DigestUtils.sha256Hex(this.toString())
