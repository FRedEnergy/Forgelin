package io.drakon.forgelin.debug

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager

/**
 * Test mod (object-style)
 */
@Suppress("unused")
@Mod(modid = "Forgelin-Workbench-Obj", name = "Forgelin Debug - Object", modLanguageAdapter = "io.drakon.forgelin.KotlinAdapter")
object ForgelinWorkbench {

    private val log = LogManager.getLogger("Workbench/Obj")

    @SidedProxy(clientSide = "io.drakon.forgelin.debug.ProxyClient")
    var clientProxy: ProxyClient? = null

    @SidedProxy(serverSide = "io.drakon.forgelin.debug.ProxyServer")
    var serverProxy: ProxyServer? = null

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