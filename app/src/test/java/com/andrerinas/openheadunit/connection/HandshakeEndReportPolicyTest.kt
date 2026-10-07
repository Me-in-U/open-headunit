package com.andrerinas.openheadunit.connection

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class HandshakeEndReportPolicyTest {

    private fun reports(
        usb: Boolean = true,
        shook: Boolean = false,
        connectionCleared: Boolean = true,
        userExit: Boolean = false,
    ) = HandshakeEndReportPolicy.reportsAfterTeardown(usb, shook, connectionCleared, userExit)

    @Test
    fun `a USB failure whose own teardown cleared the connection is reported`() {
        assertTrue(reports())
    }

    @Test
    fun `a newer connection is a preemption and is not reported`() {
        assertFalse(reports(connectionCleared = false))
    }

    @Test
    fun `a user exit during the handshake is not reported`() {
        assertFalse(reports(userExit = true))
    }

    @Test
    fun `a handshake that succeeded is never reported from the guard`() {
        assertFalse(reports(shook = true))
    }

    @Test
    fun `a socket handshake is never reported to USB`() {
        assertFalse(reports(usb = false))
    }
}
