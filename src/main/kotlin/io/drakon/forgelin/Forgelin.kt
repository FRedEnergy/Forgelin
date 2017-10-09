package io.drakon.forgelin

import cpw.mods.fml.common.Mod

/**
 * Mod wrapper for the Kotlin adapter. Used for sharing libraries when not repacking. Doesn't actually *do* anything.
 *
 * @author Arkan <arkan@drakon.io>
 */
@Mod(modid = "Forgelin", name = "Kotlin for Forge", version = "@VERSION@-@KOTLIN@", modLanguageAdapter = "io.drakon.forgelin.KotlinAdapter", acceptableRemoteVersions = "*")
@Suppress("Unused")
object Forgelin