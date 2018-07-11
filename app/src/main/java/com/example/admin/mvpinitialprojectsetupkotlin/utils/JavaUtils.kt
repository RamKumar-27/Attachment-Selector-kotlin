package com.example.admin.mvpinitialprojectsetupkotlin.utils

import java.lang.Double.parseDouble
import java.util.concurrent.TimeUnit


object JavaUtils {

    fun convertListToArray(longs: List<Long>): Array<Long> {
        return longs.toTypedArray()
    }

    fun isValidDouble(stringDouble: String?): Boolean {
        if (stringDouble == null || stringDouble.isEmpty()) {
            return false
        }
        try {
            parseDouble(stringDouble)
            return true
        } catch (e: NumberFormatException) {
            return false
        }

    }

    fun safeCastToDouble(stringDouble: String?): Double? {
        if (stringDouble == null || stringDouble.isEmpty()) {
            return null
        }
        try {
            return parseDouble(stringDouble)
        } catch (e: NumberFormatException) {
            return null
        }

    }

    fun milliSecondsToTimer(milliseconds: Long): String {

        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds))
        );
    }
}
