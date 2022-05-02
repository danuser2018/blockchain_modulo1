package blockchain

import blockchain.application.hash
import blockchain.application.mineABlock
import blockchain.config.blockchain
import blockchain.config.configureRouting
import blockchain.config.configureSerialization
import blockchain.config.updateBlockchain
import blockchain.domain.Block
import blockchain.domain.emptyBlockchain
import blockchain.infrastructure.presentation.dto.BlockDTO
import com.fasterxml.jackson.databind.SerializationFeature
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import java.text.DateFormat
import java.time.Instant

class ApplicationIT : StringSpec({

    "Test get empty blockchain" {

        testApplication {
            blockchain = emptyBlockchain(Instant.now().toEpochMilli())
            application {
                configureRouting()
                configureSerialization()
            }

            val client = createClient {
                install(ContentNegotiation) {
                    jackson { enable(SerializationFeature.INDENT_OUTPUT) }
                }
            }

            client.get("/blockchain").apply {
                status shouldBe HttpStatusCode.OK
                body<List<BlockDTO>>().apply {
                    size shouldBe 1
                    with(this[0]) {
                        index shouldBe 0
                        proof shouldBe "1"
                        previousHash shouldBe "0"
                    }
                }
            }
        }
    }

    "Test get blockchain with 2 blocks" {
        testApplication {
            blockchain = emptyBlockchain(Instant.now().toEpochMilli())
            application {
                configureRouting()
                configureSerialization()
            }

            val client = createClient {
                install(ContentNegotiation) {
                    jackson { enable(SerializationFeature.INDENT_OUTPUT) }
                }
            }

            mineABlock()

            client.get("/blockchain").apply {
                status shouldBe HttpStatusCode.OK
                body<List<BlockDTO>>().let { blockchain ->
                    blockchain.size shouldBe 2
                    with(blockchain[0]) {
                        index shouldBe 0
                        proof shouldBe "1"
                        previousHash shouldBe "0"
                    }
                    with(blockchain[1]) {
                        index shouldBe 1
                        proof shouldBe "38561"
                        previousHash shouldBe blockchain[0].hash
                    }
                }
            }
        }
    }

    "Test to add a new block" {
        testApplication {
            blockchain = emptyBlockchain(Instant.now().toEpochMilli())
            application {
                configureRouting()
                configureSerialization()
            }

            val client = createClient {
                install(ContentNegotiation) {
                    jackson { enable(SerializationFeature.INDENT_OUTPUT) }
                }
            }

            client.post("/block").apply {
                status shouldBe HttpStatusCode.OK
                body<BlockDTO>().let { block ->
                    with(blockchain.last()) {
                        block.index shouldBe index
                        block.timestamp shouldBe DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
                            .format(timestamp)
                        block.proof shouldBe proof.toInt().toString()
                        block.previousHash shouldBe previousHash
                        block.hash shouldBe this.hash()
                    }
                }
            }

            blockchain.size shouldBe 2
            with(blockchain.last()) {
                index shouldBe 1
                proof shouldBe 38561.0
                previousHash shouldBe blockchain[0].hash()
            }
        }
    }

    "Test isValid for a valid blockchain" {
        testApplication {
            blockchain = emptyBlockchain(Instant.now().toEpochMilli())
            application {
                configureRouting()
                configureSerialization()
            }

            val client = createClient {
                install(ContentNegotiation) {
                    jackson { enable(SerializationFeature.INDENT_OUTPUT) }
                }
            }

            mineABlock()

            client.get("/blockchain/validation").apply {
                status shouldBe HttpStatusCode.OK
            }
        }
    }

    "Test isValid for an invalid blockchain" {
        testApplication {
            blockchain = emptyBlockchain(Instant.now().toEpochMilli())
            application {
                configureRouting()
                configureSerialization()
            }

            val client = createClient {
                install(ContentNegotiation) {
                    jackson { enable(SerializationFeature.INDENT_OUTPUT) }
                }
            }

            updateBlockchain {
                blockchain + Block(
                    index = 0,
                    timestamp = Instant.now().toEpochMilli(),
                    proof = 1.0,
                    previousHash = "0"
                )
            }

            client.get("/blockchain/validation").apply {
                status shouldBe HttpStatusCode.InternalServerError
            }
        }
    }

})