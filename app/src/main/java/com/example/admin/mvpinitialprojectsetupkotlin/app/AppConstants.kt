package com.example.admin.mvpinitialprojectsetupkotlin.app

import java.text.SimpleDateFormat
import java.util.*

object AppConstants {

    //-------------------------------------------- Date Formatter ------------------------------------------------//
    var USER_PROFILE_TIMEZONE: TimeZone? = null

    val API_DATE_TIME_WITH_TIME_ZONE_STRING_FORMATTER = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
    val API_DATE_WITH_TIME_STRING_FORMATTER = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val API_DATE_STRING_FORMATTER = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val API_TIME_STRING_FORMATTER = SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH)

    fun changeApiRequestDateTimeFormatterTimeZone(timeZone: TimeZone) {
        API_DATE_STRING_FORMATTER.timeZone = timeZone
        API_TIME_STRING_FORMATTER.timeZone = timeZone
        API_DATE_WITH_TIME_STRING_FORMATTER.timeZone = timeZone
    }

    fun setUserProfileTimeZone(userProfileTimeZone: TimeZone) {
        USER_PROFILE_TIMEZONE = userProfileTimeZone
    }

    val FOLDER_ID = "FOLDER_ID"


}