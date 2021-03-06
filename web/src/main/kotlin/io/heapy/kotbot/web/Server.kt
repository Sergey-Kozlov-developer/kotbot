package io.heapy.kotbot.web

import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

/**
 * Create server.
 *
 * @author Ruslan Ibragimov
 * @since 1.0.0
 */
fun startServer(
    scrape: () -> String
): ShutdownServer {
    val server = embeddedServer(Netty, port = 8080) {
        JSON()

        routing {
            health()
            metrics(scrape)
        }
    }

    server.start(false)

    return { server.stop(1000, 2000) }
}

typealias ShutdownServer = () -> Unit
