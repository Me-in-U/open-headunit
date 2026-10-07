package com.andrerinas.openheadunit.aap.protocol.messages

/**
 * Which Bluetooth address service discovery announces. Android Auto can switch off the phone's
 * media audio for the address it is told, so the `bt-announce` key picks it per run.
 */
object BluetoothAnnouncePolicy {
    const val SKIP_VALUE = "SKIP_THIS_BLUETOOTH"

    enum class Mode { REAL, BLANK, SKIP }

    /** [carAddress] null means the Bluetooth service is left out. */
    data class Announce(val mode: Mode, val carAddress: String?)

    fun mode(stored: String?): Mode = when (stored?.trim()?.lowercase()) {
        "blank" -> Mode.BLANK
        "skip" -> Mode.SKIP
        else -> Mode.REAL
    }

    fun decide(stored: String?, bluetoothAddress: String): Announce = when (val m = mode(stored)) {
        Mode.REAL -> Announce(m, bluetoothAddress.ifEmpty { null })
        Mode.BLANK -> Announce(m, null)
        Mode.SKIP -> Announce(m, SKIP_VALUE)
    }
}
