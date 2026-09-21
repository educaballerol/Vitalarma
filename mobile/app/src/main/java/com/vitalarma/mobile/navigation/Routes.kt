package com.vitalarma.mobile.navigation

/**
 * Rutas de navegación de Vitalarma (mobile).
 *
 * Mantén esta lista sincronizada con las pantallas reales del Figma.
 * Los argumentos de ruta (ej. {alarmId}) se agregan solo cuando la pantalla
 * que los consume ya está implementada.
 */
object Routes {

    // Auth
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val FORGOT_PASSWORD = "forgot_password"
    const val FORGOT_PASSWORD_SENT = "forgot_password_sent"

    // Alarmas
    const val ALARM_LIST = "alarm_list"
    const val ALARM_DETAIL = "alarm_detail/{alarmId}"
    const val ALARM_DELETE_CONFIRM = "alarm_delete_confirm/{alarmId}"
    const val ALARM_TYPE_PICKER = "alarm_type_picker"
    const val ALARM_TIME_PICKER = "alarm_time_picker"
    const val ALARM_DETAILS_STEP = "alarm_details_step"
    const val ALARM_CREATED = "alarm_created/{alarmId}"

    // Ejecución de alarma
    const val ALARM_RINGING = "alarm_ringing/{alarmId}"
    const val ALARM_SCAN_OBJECT = "alarm_scan_object/{alarmId}"
    const val ALARM_COMPLETED = "alarm_completed/{alarmId}"
    const val BACKUP_TRIGGERED = "backup_triggered/{alarmId}"

    // Rutinas
    const val ROUTINE_LIST = "routine_list"
    const val ROUTINE_DETAIL = "routine_detail/{routineId}"
    const val ROUTINE_NEW = "routine_new"
    const val ROUTINE_DELETE_CONFIRM = "routine_delete_confirm/{routineId}"

    // Ajustes
    const val SETTINGS = "settings"
    const val DEVICES = "devices"
    const val CONTACTS = "contacts"
    const val ACCOUNT = "account"

    fun alarmDetail(alarmId: String) = "alarm_detail/$alarmId"
    fun alarmDeleteConfirm(alarmId: String) = "alarm_delete_confirm/$alarmId"
    fun alarmCreated(alarmId: String) = "alarm_created/$alarmId"
    fun alarmRinging(alarmId: String) = "alarm_ringing/$alarmId"
    fun alarmScanObject(alarmId: String) = "alarm_scan_object/$alarmId"
    fun alarmCompleted(alarmId: String) = "alarm_completed/$alarmId"
    fun backupTriggered(alarmId: String) = "backup_triggered/$alarmId"
    fun routineDetail(routineId: String) = "routine_detail/$routineId"
    fun routineDeleteConfirm(routineId: String) = "routine_delete_confirm/$routineId"
}