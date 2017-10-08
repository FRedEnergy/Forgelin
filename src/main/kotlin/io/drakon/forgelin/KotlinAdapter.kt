package io.drakon.forgelin

import cpw.mods.fml.common.FMLModContainer
import cpw.mods.fml.common.ILanguageAdapter
import cpw.mods.fml.common.ModContainer
import cpw.mods.fml.relauncher.Side
import org.apache.logging.log4j.LogManager
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Kotlin implementation of FML's ILanguageAdapter.
 *
 * Use by setting <pre>modLanguageAdapter = "io.drakon.forgelin.KotlinAdapter"</pre> in the Mod annotation
 * (Forge 1.8-11.14.1.1371 or above required).
 *
 * @author Arkan <arkan@drakon.io>
 */
class KotlinAdapter : ILanguageAdapter {

    companion object metadata {
        @Suppress("unused")
        val ADAPTER_VERSION = "@VERSION@-@KOTLIN@"
    }

    private val log = LogManager.getLogger(KotlinAdapter::class.java)

    override fun supportsStatics(): Boolean {
        return false
    }

    override fun setProxy(target: Field, proxyTarget: Class<*>, proxy: Any) {
        log.debug("Setting proxy: ${target.declaringClass.simpleName}.${target.name} -> $proxy")
        val proxyTargetInstance = proxyTarget.kotlin.objectInstance
        if (proxyTargetInstance != null) {
            // Singleton
            try {
                log.debug("Setting proxy on singleton target.")
                target.set(proxyTargetInstance, proxy)
            } catch (ex: Exception) {
                throw KotlinAdapterException(ex)
            }
        } else {
            log.debug("setting proxy on class target.")
            target.set(proxyTarget, proxy)
        }
    }

    override fun getNewInstance(container: FMLModContainer?, objectClass: Class<*>, classLoader: ClassLoader, factoryMarkedAnnotation: Method?): Any? {
        log.debug("FML has asked for ${objectClass.simpleName} to be constructed...")
        return if (objectClass.kotlin.objectInstance != null) {
            objectClass.kotlin.objectInstance
        } else {
            // Try looking for a class type
            log.info("Failed to get object reference, trying class construction.")
            try {
                val obj = objectClass.newInstance() ?: throw NullPointerException()
                log.info("Constructed an object from a class type ($objectClass), using that. ($obj)")
                log.warn("Hey, you, modder who owns ${objectClass.simpleName} - you should be using 'object' instead of 'class' on your @Mod class.")
                obj
            } catch (ex: Exception) {
                throw KotlinAdapterException(ex)
            }
        }
    }

    override fun setInternalProxies(mod: ModContainer?, side: Side?, loader: ClassLoader?) {
        // Nothing to do; FML's got this covered for Kotlin.
    }

    private class KotlinAdapterException(ex:Exception): RuntimeException("Kotlin adapter error - do not report to Forge!", ex)

}