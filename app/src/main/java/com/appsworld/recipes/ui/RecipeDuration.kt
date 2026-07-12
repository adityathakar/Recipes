package com.appsworld.recipes.ui

/** Formats a duration in minutes for display, e.g. 45 -> "45m", 60 -> "1h", 270 -> "4h 30m". */
internal fun Int.formatDuration(): String {
    val hours = this / 60
    val minutes = this % 60
    val parts = buildList {
        if (hours > 0) add("${hours}h")
        if (minutes > 0) add("${minutes}m")
    }
    return if (parts.isEmpty()) "0m" else parts.joinToString(" ")
}

/** Spells out a duration in minutes so TalkBack says "4 hours 30 minutes" rather than "4h 30m". */
internal fun Int.spokenDuration(): String {
    val hours = this / 60
    val minutes = this % 60
    val parts = buildList {
        if (hours > 0) add("$hours ${if (hours == 1) "hour" else "hours"}")
        if (minutes > 0) add("$minutes ${if (minutes == 1) "minute" else "minutes"}")
    }
    return if (parts.isEmpty()) "0 minutes" else parts.joinToString(" ")
}
