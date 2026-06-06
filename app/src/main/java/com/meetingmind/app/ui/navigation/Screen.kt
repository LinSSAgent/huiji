package com.meetingmind.app.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object MeetingDetail : Screen("meeting/{meetingId}") {
        fun createRoute(meetingId: Long) = "meeting/$meetingId"
    }
    data object Recording : Screen("recording/{meetingId}") {
        fun createRoute(meetingId: Long) = "recording/$meetingId"
    }
    data object CreateMeeting : Screen("create_meeting")
    data object Search : Screen("search")
    data object Settings : Screen("settings")
}
