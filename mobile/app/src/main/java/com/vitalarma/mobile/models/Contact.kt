package com.vitalarma.mobile.model

/**
 * Contacto de confianza (pantalla "Contactos"): a quién se avisa si el
 * usuario no reacciona a una alarma crítica.
 */
data class TrustedContact(
    val id: String,
    val name: String,
    val initials: String,
    val phone: String,
    val delayMinutes: Int,
    val scopeLabel: String // ej. "Solo alarmas críticas", "Solo medicamentos"
)

val sampleContacts = listOf(
    TrustedContact(
        id = "1",
        name = "Eduardo Caballero",
        initials = "EC",
        phone = "+33 6 00 08 00 00",
        delayMinutes = 10,
        scopeLabel = "Solo alarmas críticas"
    ),
    TrustedContact(
        id = "2",
        name = "Mamá",
        initials = "MA",
        phone = "+57 300 000 0000",
        delayMinutes = 5,
        scopeLabel = "Solo medicamentos"
    )
)

/**
 * Rutina (pantalla "Rutinas"): agrupa varias alarmas bajo un mismo
 * interruptor y un patrón de días.
 */
data class Routine(
    val id: String,
    val name: String,
    val alarmCount: Int,
    val daysLabel: String, // ej. "Lun a Vie"
    val enabled: Boolean = true
)

val sampleRoutines = listOf(
    Routine(id = "1", name = "Día de oficina", alarmCount = 4, daysLabel = "Lun a Vie"),
    Routine(id = "2", name = "Día de estudio", alarmCount = 3, daysLabel = "Sáb y Dom"),
    Routine(id = "3", name = "Entrenamiento", alarmCount = 2, daysLabel = "Mar y Jue")
)
