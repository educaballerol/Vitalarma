package com.vitalarma.mobile.model

data class BackupDevice(
    val id: String,
    val name: String,
    val kind: String,
    val delayMinutes: Int,
    val connected: Boolean = true
)

val sampleDevices = listOf(
    BackupDevice(id = "d1", name = "Reloj de Humberto", kind = "Wear OS", delayMinutes = 5),
    BackupDevice(id = "d2", name = "Parlante de la sala", kind = "Alexa", delayMinutes = 10)
)
