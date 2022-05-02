package blockchain

import blockchain.application.hash
import blockchain.application.mineABlock
import blockchain.config.blockchain
import blockchain.config.configureRouting
import blockchain.config.configureSerialization
import blockchain.domain.Block
import blockchain.domain.Blockchain
import blockchain.domain.emptyBlockchain
import com.fasterxml.jackson.databind.SerializationFeature
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import java.time.Instant

class ApplicationIT : StringSpec({

    "Test get empty blockchain" {

        testApplication {
            application {
                configureRouting()
                configureSerialization()
            }

            val client = createClient {
                install(ContentNegotiation) {
                    jackson { enable(SerializationFeature.INDENT_OUTPUT) }
                }
            }

            blockchain = emptyBlockchain(Instant.now().toEpochMilli())

            client.get("/blockchain").apply {
                status shouldBe HttpStatusCode.OK
                body<Blockchain>().apply {
                    size shouldBe 1
                    with(this[0]) {
                        index shouldBe 0
                        proof shouldBe 1.0
                        previousHash shouldBe "0"
                    }
                }
            }
        }
    }

//    "Test to add a new block" {
//        testApplication {
//            application {
//                configureRouting()
//                configureSerialization()
//            }
//
//            val client = createClient {
//                install(ContentNegotiation) {
//                    jackson { enable(SerializationFeature.INDENT_OUTPUT) }
//                }
//            }
//
//            blockchain = emptyBlockchain(Instant.now().toEpochMilli())
//
//            client.post("/block").apply {
//                status shouldBe HttpStatusCode.OK
//                body<Block>().apply {
//                    index shouldBe 1
//                    proof shouldBe 38561.0
//                    previousHash shouldBe blockchain[0].hash()
//                }
//            }
//        }
//    }

    "Test isValid for a valid blockchain" {
        testApplication {
            application {
                configureRouting()
                configureSerialization()
            }

            val client = createClient {
                install(ContentNegotiation) {
                    jackson { enable(SerializationFeature.INDENT_OUTPUT) }
                }
            }

            blockchain = emptyBlockchain(Instant.now().toEpochMilli())
            mineABlock()

            client.get("/blockchain/validation").apply {
                status shouldBe HttpStatusCode.OK
            }
        }
    }

//    "Test isValid for an invalid blockchain" {
//        testApplication {
//            application {
//                configureRouting()
//                configureSerialization()
//            }
//
//            val client = createClient {
//                install(ContentNegotiation) {
//                    jackson { enable(SerializationFeature.INDENT_OUTPUT) }
//                }
//            }
//
//            blockchain = emptyList()
//
//            client.get("/blockchain/validation").apply {
//                status shouldBe HttpStatusCode.InternalServerError
//            }
//        }
//    }
})