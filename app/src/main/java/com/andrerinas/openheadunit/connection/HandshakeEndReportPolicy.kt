package com.andrerinas.openheadunit.connection

object HandshakeEndReportPolicy {

    /**
     * [FIX] A failed handshake quits its transport, and that teardown clears the connection before
     * the failure branch runs; only a newer connection is a preemption.
     */
    fun reportsAfterTeardown(usb: Boolean, shook: Boolean, connectionCleared: Boolean, userExit: Boolean): Boolean =
        usb && !shook && connectionCleared && !userExit
}
