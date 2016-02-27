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

class AdapterTest {

    val adapter: KotlinAdapter = KotlinAdapter()

    @Before fun setup() {}

    @Test fun testNewInstanceObject() {
        val inst = adapter.getNewInstance(null, TestObject.javaClass, ClassLoader.getSystemClassLoader(), null)
        assertEquals(inst, TestObject)
    }

    @Test fun testNewInstanceClass() {
        val inst = adapter.getNewInstance(null, TestClass.javaClass, ClassLoader.getSystemClassLoader(), null)
        assertTrue(inst is TestClass)
    }

    @Test fun testSetInternalProxies() {} // NOOP

    @Test fun testSetProxyObject() {
        val f = TestObject.javaClass.getField("proxy")

        adapter.setProxy(f, TestObject.javaClass, ProxyClient())
        assert(TestObject.proxy is ProxyClient)

        adapter.setProxy(f, TestObject.javaClass, ProxyServer())
        assert(TestObject.proxy is ProxyServer)
    }

    @Test fun testSetProxyClass() {
        val clazz = TestClass.javaClass
        val f = clazz.getField("proxy")

        adapter.setProxy(f, clazz, ProxyClient())
        assert(TestClass.proxy is ProxyClient)

        adapter.setProxy(f, clazz, ProxyServer())
        assert(TestClass.proxy is ProxyServer)
    }

    @After fun teardown() {}

    object TestObject {
        @SidedProxy(clientSide = "io.drakon.forgelin.tests.dummy.ProxyClient", serverSide = "io.drakon.forgelin.tests.dummy.ProxyServer")
        @JvmField var proxy: Proxy? = null
    }

    object TestClass {
        @SidedProxy(clientSide = "io.drakon.forgelin.tests.dummy.ProxyClient", serverSide = "io.drakon.forgelin.tests.dummy.ProxyServer")
        @JvmStatic var proxy: Proxy? = null
    }
}