package io.drakon.forgelin.tests

import cpw.mods.fml.common.SidedProxy
import io.drakon.forgelin.KotlinAdapter
import io.drakon.forgelin.tests.dummy.Proxy
import io.drakon.forgelin.tests.dummy.ProxyClient
import io.drakon.forgelin.tests.dummy.ProxyServer
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test

public class AdapterTest {

    val adapter: KotlinAdapter = KotlinAdapter()

    @Before fun setup() {}

    @Test fun testNewInstanceObject() {
        val inst = adapter.getNewInstance(null, TestObject.javaClass, ClassLoader.getSystemClassLoader(), null)
        assertEquals(inst, TestObject)
    }

    @Test fun testNewInstanceClass() {
        val inst = adapter.getNewInstance(null, javaClass<TestClass>(), ClassLoader.getSystemClassLoader(), null)
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
        // For whatever reason calling 'javaClass' gets us the internal companion class, instead of the class itself
        val clazz = javaClass<TestClass>()
        val f = clazz.getField("proxy")

        adapter.setProxy(f, clazz, ProxyClient())
        assert(TestClass.proxy is ProxyClient)

        adapter.setProxy(f, clazz, ProxyServer())
        assert(TestClass.proxy is ProxyServer)
    }

    @After fun teardown() {}

    public object TestObject {
        @SidedProxy(clientSide = "io.drakon.forgelin.tests.dummy.ProxyClient", serverSide = "io.drakon.forgelin.tests.dummy.ProxyServer")
        public var proxy: Proxy? = null
    }

    public object TestClass {
        @SidedProxy(clientSide = "io.drakon.forgelin.tests.dummy.ProxyClient", serverSide = "io.drakon.forgelin.tests.dummy.ProxyServer")
        @JvmStatic public var proxy: Proxy? = null
    }
}