package com.vitalarma.mobile.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vitalarma.mobile.model.sampleAlarms
import com.vitalarma.mobile.model.AlarmType
import com.vitalarma.mobile.ui.screens.alarms.AlarmDetailScreen
import com.vitalarma.mobile.ui.screens.alarms.AlarmRingingScreen
import com.vitalarma.mobile.ui.screens.alarms.AlarmListScreen
import com.vitalarma.mobile.ui.screens.alarms.CreateAlarmTimeScreen
import com.vitalarma.mobile.ui.screens.alarms.CreateAlarmTypeScreen
import com.vitalarma.mobile.ui.screens.alarms.DeleteAlarmScreen

/**
 * Punto único de navegación de la app.
 *
 * M-04, M-05 y M-06 ya están implementadas con datos reales (ver
 * ui/screens/alarms/). El resto sigue en PlaceholderScreen.
 */
@Composable
fun VitalarmaNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.ALARM_LIST
) {
    NavHost(navController = navController, startDestination = startDestination) {

        // Auth
        composable(Routes.LOGIN) { PlaceholderScreen("Login") }
        composable(Routes.REGISTER) { PlaceholderScreen("Crear cuenta") }
        composable(Routes.FORGOT_PASSWORD) { PlaceholderScreen("Recuperar contraseña") }
        composable(Routes.FORGOT_PASSWORD_SENT) { PlaceholderScreen("Enlace enviado") }

        // Alarmas
        composable(Routes.ALARM_LIST) {
            AlarmListScreen(
                currentRoute = Routes.ALARM_LIST,
                onNavigate = { route -> navController.navigate(route) },
                onAlarmClick = { alarm -> navController.navigate(Routes.alarmDetail(alarm.id)) },
                onCreateAlarmClick = { navController.navigate(Routes.ALARM_TIME_PICKER) },
                onTestCriticalClick = { navController.navigate(Routes.alarmRinging(sampleAlarms.first().id)) }
            )
        }
        composable(
            route = Routes.ALARM_DETAIL,
            arguments = listOf(navArgument("alarmId") { type = NavType.StringType })
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getString("alarmId")
            val alarm = sampleAlarms.find { it.id == alarmId } ?: sampleAlarms.first()
            AlarmDetailScreen(
                alarm = alarm,
                currentRoute = Routes.ALARM_LIST,
                onNavigate = { route -> navController.navigate(route) },
                onBackClick = { navController.popBackStack() },
                onSaveClick = { navController.popBackStack() },
                onDeleteClick = { navController.navigate(Routes.alarmDeleteConfirm(alarm.id)) }
            )
        }
        composable(
            route = Routes.ALARM_DELETE_CONFIRM,
            arguments = listOf(navArgument("alarmId") { type = NavType.StringType })
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getString("alarmId")
            val alarm = sampleAlarms.find { it.id == alarmId } ?: sampleAlarms.first()
            DeleteAlarmScreen(
                alarm = alarm,
                currentRoute = Routes.ALARM_LIST,
                onNavigate = { route -> navController.navigate(route) },
                onBackClick = { navController.popBackStack() },
                onCancelClick = { navController.popBackStack() },
                onConfirmDeleteClick = { navController.popBackStack(Routes.ALARM_LIST, inclusive = false) }
            )
        }

        composable(Routes.ALARM_TIME_PICKER) {
            CreateAlarmTimeScreen(
                onBackClick = { navController.popBackStack() },
                onNextClick = { _, _ -> navController.navigate(Routes.ALARM_TYPE_PICKER) },
                onCreateReminderClick = {
                    navController.popBackStack(Routes.ALARM_LIST, inclusive = false)
                }
            )
        }
        composable(Routes.ALARM_TYPE_PICKER) {
            CreateAlarmTypeScreen(
                onBackClick = { navController.popBackStack() },
                onNextClick = { _: AlarmType -> navController.navigate(Routes.ALARM_DETAILS_STEP) },
                onCancelClick = {
                    navController.popBackStack(Routes.ALARM_LIST, inclusive = false)
                }
            )
        }
        composable(Routes.ALARM_DETAILS_STEP) { PlaceholderScreen("Detalles") }
        composable(Routes.ALARM_CREATED) { PlaceholderScreen("Alarma creada") }

        // Ejecución
        composable(
            route = Routes.ALARM_RINGING,
            arguments = listOf(navArgument("alarmId") { type = NavType.StringType })
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getString("alarmId")
            val alarm = sampleAlarms.find { it.id == alarmId } ?: sampleAlarms.first()
            AlarmRingingScreen(
                alarm = alarm,
                onStopClick = { navController.navigate(Routes.alarmScanObject(alarm.id)) }
            )
        }
        composable(Routes.ALARM_SCAN_OBJECT) { PlaceholderScreen("Escanea el objeto") }
        composable(Routes.ALARM_COMPLETED) { PlaceholderScreen("Alarma cumplida") }
        composable(Routes.BACKUP_TRIGGERED) { PlaceholderScreen("Respaldo activado") }

        // Rutinas
        composable(Routes.ROUTINE_LIST) { PlaceholderScreen("Rutinas") }
        composable(Routes.ROUTINE_DETAIL) { PlaceholderScreen("Detalle de rutina") }
        composable(Routes.ROUTINE_NEW) { PlaceholderScreen("Nueva rutina") }
        composable(Routes.ROUTINE_DELETE_CONFIRM) { PlaceholderScreen("Eliminar rutina") }

        // Ajustes
        composable(Routes.SETTINGS) { PlaceholderScreen("Ajustes") }
        composable(Routes.DEVICES) { PlaceholderScreen("Dispositivos") }
        composable(Routes.CONTACTS) { PlaceholderScreen("Contactos") }
        composable(Routes.ACCOUNT) { PlaceholderScreen("Cuenta") }
    }
}

@Composable
private fun PlaceholderScreen(name: String) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "TODO: $name", style = MaterialTheme.typography.titleMedium)
        }
    }
}
