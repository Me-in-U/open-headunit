package com.andrerinas.openheadunit.aap.protocol.messages

import com.andrerinas.openheadunit.utils.SettingsBackupManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class BluetoothAnnouncePolicyTest {
    private val mac = "AA:BB:CC:DD:EE:FF"

    @Test
    fun `no stored mode announces the real address`() {
        val a = BluetoothAnnouncePolicy.decide(null, mac)
        assertEquals(BluetoothAnnouncePolicy.Mode.REAL, a.mode)
        assertEquals(mac, a.carAddress)
    }

    @Test
    fun `no stored mode and no address omits the service`() {
        val a = BluetoothAnnouncePolicy.decide(null, "")
        assertEquals(BluetoothAnnouncePolicy.Mode.REAL, a.mode)
        assertNull(a.carAddress)
    }

    @Test
    fun `real announces the address`() {
        assertEquals(mac, BluetoothAnnouncePolicy.decide("real", mac).carAddress)
    }

    @Test
    fun `blank omits the service even with an address`() {
        val a = BluetoothAnnouncePolicy.decide("blank", mac)
        assertEquals(BluetoothAnnouncePolicy.Mode.BLANK, a.mode)
        assertNull(a.carAddress)
    }

    @Test
    fun `skip announces the skip value`() {
        val a = BluetoothAnnouncePolicy.decide("skip", mac)
        assertEquals(BluetoothAnnouncePolicy.Mode.SKIP, a.mode)
        assertEquals("SKIP_THIS_BLUETOOTH", a.carAddress)
    }

    @Test
    fun `skip announces the skip value with no address`() {
        assertEquals("SKIP_THIS_BLUETOOTH", BluetoothAnnouncePolicy.decide("skip", "").carAddress)
    }

    @Test
    fun `mode ignores case and spaces`() {
        assertEquals(BluetoothAnnouncePolicy.Mode.SKIP, BluetoothAnnouncePolicy.mode(" Skip "))
    }

    @Test
    fun `an unknown value falls back to real`() {
        assertEquals(BluetoothAnnouncePolicy.Mode.REAL, BluetoothAnnouncePolicy.mode("forbid"))
        assertEquals(BluetoothAnnouncePolicy.Mode.REAL, BluetoothAnnouncePolicy.mode(""))
    }

    @Test
    fun announceKeyIsRegisteredInBackupManager() {
        val type = SettingsBackupManager.backupKeys["bt-announce"]
        assertNotNull("bt-announce must be registered in SettingsBackupManager", type)
        assertEquals(SettingsBackupManager.ValueType.STRING, type)
    }
}
