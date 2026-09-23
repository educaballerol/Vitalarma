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
import com.vitalarma.mobile.ui.screens.alarms.AlarmCompletedScreen
import com.vitalarma.mobile.ui.screens.alarms.AlarmCreatedScreen
import com.vitalarma.mobile.ui.screens.alarms.AlarmDetailScreen
import com.vitalarma.mobile.ui.screens.alarms.AlarmRingingScreen
import com.vitalarma.mobile.ui.screens.alarms.BackupTriggeredScreen
import com.vitalarma.mobile.ui.screens.alarms.AlarmListScreen
import com.vitalarma.mobile.ui.screens.alarms.CreateAlarmDetailsScreen
import com.vitalarma.mobile.ui.screens.alarms.CreateAlarmTimeScreen
import com.vitalarma.mobile.ui.screens.alarms.CreateAlarmTypeScreen
import com.vitalarma.mobile.ui.screens.alarms.DeleteAlarmScreen
import com.vitalarma.mobile.ui.screens.alarms.ScanObjectScreen
import com.vitalarma.mobile.model.sampleRoutines
import com.vitalarma.mobile.ui.screens.routines.DeleteRoutineScreen
import com.vitalarma.mobile.ui.screens.routines.RoutineListScreen
import com.vitalarma.mobile.ui.screens.settings.DevicesScreen
import com.vitalarma.mobile.ui.screens.settings.SettingsScreen

/**
 * Punto único de navegación de la app.
 *
 * M-04, M-05, M-06, M-08 y M-09 ya están implementadas con datos reales.
 * El resto sigue en PlaceholderScreen.
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
                // Antes descartaba el tipo elegido (onNextClick = { _: AlarmType -> ... });
                // ahora se lo pasamos a M-09 como argumento de ruta.
                onNextClick = { tipo: AlarmType -> navController.navigate(Routes.alarmDetailsStep(tipo.name)) },
                onCancelClick = {
                    navController.popBackStack(Routes.ALARM_LIST, inclusive = false)
                }
            )
        }
        composable(
            route = Routes.ALARM_DETAILS_STEP,
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) { backStackEntry ->
            val typeName = backStackEntry.arguments?.getString("type")
            val alarmType = AlarmType.entries.find { it.name == typeName } ?: AlarmType.CRITICA
            CreateAlarmDetailsScreen(
                alarmType = alarmType,
                onBackClick = { navController.popBackStack() },
                onCreateAlarmClick = {
                    navController.navigate(Routes.alarmCreated(sampleAlarms.first().id)) {
                        popUpTo(Routes.ALARM_LIST)
                    }
                }
            )
        }
        composable(
            route = Routes.ALARM_CREATED,
            arguments = listOf(navArgument("alarmId") { type = NavType.StringType })
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getString("alarmId")
            val alarm = sampleAlarms.find { it.id == alarmId } ?: sampleAlarms.first()
            AlarmCreatedScreen(
                alarm = alarm,
                onSeeAlarmsClick = {
                    navController.popBackStack(Routes.ALARM_LIST, inclusive = false)
                },
                onCreateAnotherClick = {
                    navController.navigate(Routes.ALARM_TIME_PICKER) {
                        popUpTo(Routes.ALARM_LIST)
                    }
                }
            )
        }

        // Ejecución
        composable(
            route = Routes.ALARM_RINGING,
            arguments = listOf(navArgument("alarmId") { type = NavType.StringType })
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getString("alarmId")
            val alarm = sampleAlarms.find { it.id == alarmId } ?: sampleAlarms.first()
            AlarmRingingScreen(
                alarm = alarm,
                onStopClick = { navController.navigate(Routes.alarmScanObject(alarm.id)) },
                onBackupTriggered = { navController.navigate(Routes.backupTriggered(alarm.id)) }
            )
        }
        composable(
            route = Routes.ALARM_SCAN_OBJECT,
            arguments = listOf(navArgument("alarmId") { type = NavType.StringType })
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getString("alarmId")
            val alarm = sampleAlarms.find { it.id == alarmId } ?: sampleAlarms.first()
            ScanObjectScreen(
                onScanSuccess = { navController.navigate(Routes.alarmCompleted(alarm.id)) }
            )
        }
        composable(
            route = Routes.ALARM_COMPLETED,
            arguments = listOf(navArgument("alarmId") { type = NavType.StringType })
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getString("alarmId")
            val alarm = sampleAlarms.find { it.id == alarmId } ?: sampleAlarms.first()
            AlarmCompletedScreen(
                alarm = alarm,
                onSeeAlarmsClick = {
                    navController.popBackStack(Routes.ALARM_LIST, inclusive = false)
                }
            )
        }
        composable(
            route = Routes.BACKUP_TRIGGERED,
            arguments = listOf(navArgument("alarmId") { type = NavType.StringType })
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getString("alarmId")
            val alarm = sampleAlarms.find { it.id == alarmId } ?: sampleAlarms.first()
            BackupTriggeredScreen(
                alarm = alarm,
                onAwakeClick = { navController.navigate(Routes.alarmScanObject(alarm.id)) },
                onCancelClick = {
                    navController.popBackStack(Routes.ALARM_LIST, inclusive = false)
                }
            )
        }

        // Rutinas
        composable(Routes.ROUTINE_LIST) {
            RoutineListScreen(
                currentRoute = Routes.ROUTINE_LIST,
                onNavigate = { route -> navController.navigate(route) },
                onRoutineClick = { routine -> navController.navigate(Routes.routineDetail(routine.id)) },
                onRoutineLongClick = { routine ->
                    navController.navigate(Routes.routineDeleteConfirm(routine.id))
                },
                onCreateRoutineClick = { navController.navigate(Routes.ROUTINE_NEW) }
            )
        }
        composable(Routes.ROUTINE_DETAIL) { PlaceholderScreen("Detalle de rutina") }
        composable(Routes.ROUTINE_NEW) { PlaceholderScreen("Nueva rutina") }
        composable(
            route = Routes.ROUTINE_DELETE_CONFIRM,
            arguments = listOf(navArgument("routineId") { type = NavType.StringType })
        ) { backStackEntry ->
            val routineId = backStackEntry.arguments?.getString("routineId")
            val routine = sampleRoutines.find { it.id == routineId } ?: sampleRoutines.first()
            DeleteRoutineScreen(
                routine = routine,
                onBackClick = { navController.popBackStack() },
                onCancelClick = { navController.popBackStack() },
                onConfirmDeleteClick = {
                    navController.popBackStack(Routes.ROUTINE_LIST, inclusive = false)
                }
            )
        }

        // Ajustes
        composable(Routes.SETTINGS) {
            SettingsScreen(
                currentRoute = Routes.SETTINGS,
                onNavigate = { route -> navController.navigate(route) },
                onDevicesClick = { navController.navigate(Routes.DEVICES) },
                onContactsClick = { navController.navigate(Routes.CONTACTS) },
                onAccountClick = { navController.navigate(Routes.ACCOUNT) },
                onScheduleClick = { }
            )
        }
        composable(Routes.DEVICES) {
            DevicesScreen(
                onBackClick = { navController.popBackStack() },
                onLinkDeviceClick = { }
            )
        }
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
