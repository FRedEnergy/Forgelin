package io.drakon.forgelin.debug

import org.apache.logging.log4j.LogManager

/**
 * A test proxy, what do you want from me?! D=
 *
 * Client-side.
 */
class ProxyClient : Proxy {

    init {
        LogManager.getLogger().info("Client proxy constructed.")
    }

}