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

        val proxyField = testObjectClass.memberProperties.find {
            it.name == "proxy"
        }!!.javaField!!

        adapter.setProxy(proxyField, testObjectClass.java, ProxyClient())
        assert(TestObject.proxy is ProxyClient)

        adapter.setProxy(proxyField, testObjectClass.java, ProxyServer())
        assert(TestObject.proxy is ProxyServer)
    }

    @Test fun testSetProxyClass() {
        val testClass = TestClass::class

        val testClassCompanion = testClass.companionObject!!

        val proxyField = testClassCompanion.memberProperties.find {
            it.name == "proxy"
        }!!.javaField!!

        adapter.setProxy(proxyField, testClass.java, ProxyClient())
        assert(TestClass.proxy is ProxyClient)

        adapter.setProxy(proxyField, testClass.java, ProxyServer())
        assert(TestClass.proxy is ProxyServer)
    }

    @After fun teardown() {}

}

object TestObject {
    @SidedProxy(serverSide = "io.drakon.forgelin.tests.dummy.ProxyServer", clientSide = "io.drakon.forgelin.tests.dummy.ProxyClient")
    lateinit var proxy: Proxy
}

class TestClass {

    companion object {
        @SidedProxy(serverSide = "io.drakon.forgelin.tests.dummy.ProxyServer", clientSide = "io.drakon.forgelin.tests.dummy.ProxyClient")
        lateinit var proxy: Proxy
    }

}