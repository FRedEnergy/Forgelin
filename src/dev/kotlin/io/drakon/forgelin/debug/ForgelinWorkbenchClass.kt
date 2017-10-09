package io.drakon.forgelin.debug

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager

/**
 * Test mod (class-style)
 */
@Mod(modid = "Forgelin-Workbench-Class", name = "Forgelin Debug - Class", modLanguageAdapter = "io.drakon.forgelin.KotlinAdapter")
@Suppress("unused")
class ForgelinWorkbenchClass {

    private val log = LogManager.getLogger("Workbench/Cls")

    companion object {

        @SidedProxy(clientSide = "io.drakon.forgelin.debug.ProxyClient")
        var clientProxy: ProxyClient? = null

        @SidedProxy(serverSide = "io.drakon.forgelin.debug.ProxyServer")
        var serverProxy: ProxyServer? = null

    }

    @Suppress("UNUSED_PARAMETER")
    @EventHandler
    fun preinit(evt: FMLPreInitializationEvent) {
        log.info("Preinit.")
    }

    @Suppress("UNUSED_PARAMETER")
    @EventHandler
    fun init(evt: FMLInitializationEvent) {
        log.info("Init.")
    }

    @Suppress("UNUSED_PARAMETER")
    @EventHandler
    fun postinit(evt: FMLPostInitializationEvent) {
        log.info("Postinit.")
    }

}