package blockchain

import blockchain.infrastructure.plugins.configureRouting
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*

class ApplicationIT : StringSpec({
    "Test root" {
        testApplication {
            application {
                configureRouting()
            }
            client.get("/").apply {
                status shouldBe HttpStatusCode.OK
                bodyAsText() shouldBe "Hello World!"
            }
        }
    }
})