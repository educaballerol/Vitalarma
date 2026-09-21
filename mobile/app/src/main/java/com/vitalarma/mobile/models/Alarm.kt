package com.vitalarma.mobile.model

/**
 * Tipos de alarma definidos en el diseño (ver pantalla "Escoge un tipo").
 */
enum class AlarmType(val label: String) {
    CRITICA("CRÍTICA"),
    CAUTA("CAUTA"),
    COMPARTIDA("COMPARTIDA"),
    RECORDATORIO("RECORDATORIO")
}

/**
 * Solo estructura de datos para poblar la UI estática. Sin persistencia,
 * sin backend: los valores por defecto son los que aparecen en el mockup
 * de "Mis Alarmas" para que las pantallas se vean con contenido real.
 */
data class Alarm(
    val id: String,
    val time: String,          // ej. "6:00 A.M"
    val type: AlarmType,
    val title: String,
    val lifeArea: String = "",
    val repeatDays: List<String> = emptyList(),
    val enabled: Boolean = true
)

/**
 * Datos de ejemplo tomados directamente del Figma para que las pantallas
 * no se vean vacías mientras las maquetas. Reemplaza o amplía según la
 * pantalla que estés construyendo.
 */
val sampleAlarms = listOf(
    Alarm(
        id = "1",
        time = "6:00 A.M",
        type = AlarmType.CRITICA,
        title = "Salir hacia la oficina",
        lifeArea = "Trabajo",
        repeatDays = listOf("Lun", "Mar", "Mié", "Jue", "Vie")
    ),
    Alarm(
        id = "2",
        time = "1:00 P.M",
        type = AlarmType.CAUTA,
        title = "Reunión con el cliente"
    ),
    Alarm(
        id = "3",
        time = "7:00 P.M",
        type = AlarmType.RECORDATORIO,
        title = "Entrega UX"
    ),
    Alarm(
        id = "4",
        time = "8:00 A.M",
        type = AlarmType.COMPARTIDA,
        title = "Medicina de mamá"
    )
)
