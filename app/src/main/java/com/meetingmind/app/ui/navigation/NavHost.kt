package com.meetingmind.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.meetingmind.app.ui.home.HomeScreen
import com.meetingmind.app.ui.meeting.MeetingDetailScreen
import com.meetingmind.app.ui.meeting.CreateMeetingScreen
import com.meetingmind.app.ui.recording.RecordingScreen
import com.meetingmind.app.ui.search.SearchScreen
import com.meetingmind.app.ui.settings.SettingsScreen

@Composable
fun MeetingMindNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onMeetingClick = { meetingId ->
                    navController.navigate(Screen.MeetingDetail.createRoute(meetingId))
                },
                onCreateMeeting = {
                    navController.navigate(Screen.CreateMeeting.route)
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(
            route = Screen.MeetingDetail.route,
            arguments = listOf(navArgument("meetingId") { type = NavType.LongType })
        ) { backStackEntry ->
            val meetingId = backStackEntry.arguments?.getLong("meetingId") ?: return@composable
            MeetingDetailScreen(
                meetingId = meetingId,
                onNavigateBack = { navController.popBackStack() },
                onStartRecording = {
                    navController.navigate(Screen.Recording.createRoute(meetingId))
                }
            )
        }

        composable(
            route = Screen.Recording.route,
            arguments = listOf(navArgument("meetingId") { type = NavType.LongType })
        ) { backStackEntry ->
            val meetingId = backStackEntry.arguments?.getLong("meetingId") ?: return@composable
            RecordingScreen(
                meetingId = meetingId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CreateMeeting.route) {
            CreateMeetingScreen(
                onNavigateBack = { navController.popBackStack() },
                onMeetingCreated = { meetingId ->
                    navController.popBackStack()
                    navController.navigate(Screen.MeetingDetail.createRoute(meetingId))
                }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onMeetingClick = { meetingId ->
                    navController.navigate(Screen.MeetingDetail.createRoute(meetingId))
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
