package blockchain.config

import blockchain.application.isValid
import blockchain.application.mineABlock
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        post("/block") { call.respond(OK, mineABlock()) }

        get("/blockchain") { call.respond(OK, blockchain) }

        get("/blockchain/validation") {
            when {
                blockchain.isValid() -> call.respond(OK, "The chain is valid")
                else -> call.respond(InternalServerError, "The chain is invalid")
            }
        }
    }
}
