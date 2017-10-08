package io.drakon.forgelin.tests

import cpw.mods.fml.common.SidedProxy
import io.drakon.forgelin.KotlinAdapter
import io.drakon.forgelin.tests.dummy.Proxy
import io.drakon.forgelin.tests.dummy.ProxyClient
import io.drakon.forgelin.tests.dummy.ProxyServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class AdapterTest {

    private val adapter: KotlinAdapter = KotlinAdapter()

    @Before fun setup() {}

    @Test fun testNewInstanceObject() {
        val inst = adapter.getNewInstance(null, TestObject::class.java, ClassLoader.getSystemClassLoader(), null)
        assertEquals(inst, TestObject)
    }

    @Test fun testNewInstanceClass() {
        val inst = adapter.getNewInstance(null, TestClass::class.java, ClassLoader.getSystemClassLoader(), null)
        assertTrue(inst is TestClass)
    }

    @Test fun testSetInternalProxies() {} // NOOP

    @Test fun testSetProxyObject() {
        val testObjectClass = TestObject::class

        val clientProxyField = testObjectClass.memberProperties.find {
            it.name == "clientProxy"
        }!!.javaField!!
        val serverProxyField = testObjectClass.memberProperties.find {
            it.name == "serverProxy"
        }!!.javaField!!

        adapter.setProxy(clientProxyField, testObjectClass.java, ProxyClient())
        assert(TestObject.clientProxy is ProxyClient)

        adapter.setProxy(serverProxyField, testObjectClass.java, ProxyServer())
        assert(TestObject.serverProxy is ProxyServer)
    }

    @Test fun testSetProxyClass() {
        val testClass = TestClass::class

        val testClassCompanion = testClass.companionObject!!

        val clientProxyField = testClassCompanion.memberProperties.find {
            it.name == "clientProxy"
        }!!.javaField!!
        val serverProxyField = testClassCompanion.memberProperties.find {
            it.name == "serverProxy"
        }!!.javaField!!

        adapter.setProxy(clientProxyField, testClass.java, ProxyClient())
        assert(TestClass.clientProxy is ProxyClient)

        adapter.setProxy(serverProxyField, testClass.java, ProxyServer())
        assert(TestClass.serverProxy is ProxyServer)
    }

    @After fun teardown() {}

}

object TestObject {
    @SidedProxy(clientSide = "io.drakon.forgelin.tests.dummy.ProxyClient")
    @JvmField
    var clientProxy: Proxy? = null
    @SidedProxy(serverSide = "io.drakon.forgelin.tests.dummy.ProxyServer")
    @JvmField
    var serverProxy: Proxy? = null
}

class TestClass {

    companion object {
        @SidedProxy(clientSide = "io.drakon.forgelin.tests.dummy.ProxyClient")
        @JvmField
        var clientProxy: Proxy? = null
        @SidedProxy(serverSide = "io.drakon.forgelin.tests.dummy.ProxyServer")
        @JvmField
        var serverProxy: Proxy? = null
    }

}