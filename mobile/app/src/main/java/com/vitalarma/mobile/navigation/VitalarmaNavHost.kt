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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vitalarma.mobile.ui.screens.alarms.AlarmListScreen

/**
 * Versión reducida temporal: solo registra M-04 (AlarmListScreen) para que
 * el proyecto compile mientras terminas de copiar AlarmDetailScreen.kt y
 * DeleteAlarmScreen.kt. Los onAlarmClick/onCreateAlarmClick/onTestCriticalClick
 * no navegan a nada todavía (quedan como no-op) porque esas rutas están
 * comentadas más abajo.
 *
 * TODO: cuando ya tengas AlarmDetailScreen.kt y DeleteAlarmScreen.kt
 * copiados y con el paquete com.vitalarma.mobile correcto, descomenta las
 * rutas ALARM_DETAIL y ALARM_DELETE_CONFIRM (bloque comentado al final) y
 * restaura los tres callbacks de AlarmListScreen a como estaban antes
 * (navegando con Routes.alarmDetail(...), etc.) en vez de {}.
 */
@Composable
fun VitalarmaNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.ALARM_LIST
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.ALARM_LIST) {
            AlarmListScreen(
                currentRoute = Routes.ALARM_LIST,
                onNavigate = { /* TODO: reactivar cuando existan Rutinas/Ajustes */ },
                onAlarmClick = { /* TODO: reactivar cuando exista AlarmDetailScreen */ },
                onCreateAlarmClick = { /* TODO: reactivar cuando exista el flujo de creación */ },
                onTestCriticalClick = { /* TODO: reactivar cuando exista M-11 */ }
            )
        }

        /*
        // Descomenta este bloque cuando tengas AlarmDetailScreen.kt y
        // DeleteAlarmScreen.kt copiados en ui/screens/alarms/, y vuelve a
        // importar androidx.navigation.NavType, androidx.navigation.navArgument,
        // com.vitalarma.mobile.model.sampleAlarms,
        // com.vitalarma.mobile.ui.screens.alarms.AlarmDetailScreen y
        // com.vitalarma.mobile.ui.screens.alarms.DeleteAlarmScreen arriba.

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
        */
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