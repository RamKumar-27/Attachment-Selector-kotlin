package ram.attachmentSelector.utils

import org.joda.time.*
import org.joda.time.format.DateTimeFormat
import ram.attachmentSelector.app.AppConstants
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateTimeUtils {

    val TAG = LoggerUtils.makeLogTag(DateTimeUtils::class.java)

    private val localTimeZoneCalendar = Calendar.getInstance()
    private val dateTimeformatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZ")
    private val DISPLAY_DATE_TIME_FORMATTER = SimpleDateFormat("MM/dd/yy , hh:mm a")
    private val DISPLAY_DATE_FORMATTER = SimpleDateFormat("MM/dd/yy")

    val tomorrowsDate: Date
        get() {
            val calendar = Calendar.getInstance()
            calendar.roll(Calendar.DATE, true)
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE), 0, 0, 0)
            return calendar.time
        }

    fun dateAndTimeDifference(inputDate: Date?): String? {
        if (inputDate == null) {
            return null
        }
        val mBuffer = StringBuilder()
        try {
            val startTime = DateTime(inputDate)
            val endTime = DateTime(Date())
            val years = Years.yearsBetween(startTime, endTime).years
            val months = Months.monthsBetween(startTime, endTime).months
            val weeks = Weeks.weeksBetween(startTime, endTime).weeks
            val days = Days.daysBetween(startTime, endTime).days
            val hours = Hours.hoursBetween(startTime, endTime).hours
            val minutes = Minutes.minutesBetween(startTime, endTime).minutes
            if (years > 0) {
                mBuffer.append(years)
                if (years > 1) {
                    mBuffer.append(" years ago")
                } else {
                    mBuffer.append(" year ago")
                }
            } else if (months > 0) {
                mBuffer.append(months)
                if (months > 1) {
                    mBuffer.append(" months ago")
                } else {
                    mBuffer.append(" month ago")
                }
            } else if (weeks > 0) {
                mBuffer.append(weeks)
                if (weeks > 1) {
                    mBuffer.append(" weeks ago")
                } else {
                    mBuffer.append(" week ago")
                }
            } else if (days > 0) {
                mBuffer.append(days)
                if (days > 1) {
                    mBuffer.append(" days ago")
                } else {
                    mBuffer.append(" day ago")
                }
            } else if (hours > 0) {
                mBuffer.append(hours)
                if (hours > 1) {
                    mBuffer.append(" hours ago")
                } else {
                    mBuffer.append(" hour ago")
                }
            } else {
                if (minutes > 3) {
                    mBuffer.append(minutes).append(" minutes ago")
                } else {
                    mBuffer.append("Just now")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return mBuffer.toString()
    }

    fun getDisplayDate(input: String?): String {
        if (input == null) return ""
        val dateTime = DateTime.parse(input)
        val fmt = DateTimeFormat.forPattern("MM/dd/yy")
        return fmt.print(dateTime)
    }

    fun getDisplayMonth(input: Date?): String {
        if (input == null) return ""
        val simpleDateFormat = SimpleDateFormat("MMM yyyy")
        simpleDateFormat.timeZone = AppConstants.USER_PROFILE_TIMEZONE
        return simpleDateFormat.format(input)
    }

    fun getDisplayDate(input: Date?): String {
        if (input == null) return ""
        val simpleDateFormat = SimpleDateFormat("MM/dd/yy")
        simpleDateFormat.timeZone = AppConstants.USER_PROFILE_TIMEZONE
        return simpleDateFormat.format(input)
    }

    fun getStringFromDate(input: Date?): String {
        if (input == null) return ""
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        simpleDateFormat.timeZone = AppConstants.USER_PROFILE_TIMEZONE
        return simpleDateFormat.format(input)
    }

    fun getDisplayDateWithTime(input: String?): String? {
        return if (input == null) "" else getDisplayDateWithTime(DateTime.parse(input).toDate())
    }

    fun getDisplayDayOfMonth(date: Date?): String? {
        if (date == null) return null

        val outFormat = SimpleDateFormat("dd")
        outFormat.timeZone = AppConstants.USER_PROFILE_TIMEZONE
        return outFormat.format(date)
    }

    fun getDisplayTime(date: Date?): String? {
        if (date == null) {
            return null
        }
        val simpleDateFormat = SimpleDateFormat("hh:mm a")
        simpleDateFormat.timeZone = AppConstants.USER_PROFILE_TIMEZONE
        return simpleDateFormat.format(date)
    }

    fun getDisplayDateWithTime(date: Date?): String? {
        if (date == null) {
            return null
        }
        DISPLAY_DATE_TIME_FORMATTER.timeZone = AppConstants.USER_PROFILE_TIMEZONE
        return DISPLAY_DATE_TIME_FORMATTER.format(date)
    }

    fun getDayStartDate(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE), 0, 0, 0)
        return calendar.time
    }

    fun getDayEndDate(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE), 23, 59, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    fun getDateFromDateTime(dateTime: Date?): Date? {
        if (dateTime == null) {
            return null
        }
        val calendar = Calendar.getInstance()
        calendar.time = dateTime
        val date = Calendar.getInstance()
        date.clear()
        date.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        date.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        date.set(Calendar.DATE, calendar.get(Calendar.DATE))
        return date.time
    }

    fun getTimeFromDateTime(dateTime: Date?): Date? {
        if (dateTime == null) {
            return null
        }
        val calendar = Calendar.getInstance()
        calendar.time = dateTime
        val time = Calendar.getInstance()
        time.clear()
        time.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY))
        time.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE))
        time.set(Calendar.SECOND, calendar.get(Calendar.SECOND))
        time.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND))
        return time.time
    }

    fun getDurationAddedDate(dateTime: Date, hour: Int, minute: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = dateTime
        calendar.add(Calendar.HOUR, hour)
        calendar.add(Calendar.MINUTE, minute)

        return calendar.time
    }

    fun getDurationAddedDate(dateTime: Date, hoursAndMinutes: Double): Date {
        val hoursAndMins = convertDoubleToHoursAndMin(hoursAndMinutes)
        return getDurationAddedDate(dateTime, hoursAndMins[0], hoursAndMins[1])
    }

    fun getDurationSubtractedDate(dateTime: Date, hour: Int, minute: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = dateTime
        calendar.add(Calendar.HOUR, -hour)
        calendar.add(Calendar.MINUTE, -minute)

        return calendar.time
    }

    fun getDurationSubtractedDate(dateTime: Date, hoursAndMinutes: Double): Date {
        val hoursAndMins = convertDoubleToHoursAndMin(hoursAndMinutes)
        return getDurationSubtractedDate(dateTime, hoursAndMins[0], hoursAndMins[1])
    }

    fun getStringTimeDiffInHoursAndMin(startDateTime: Date, finishDateTime: Date,
                                       divider: String): String {
        val diffInMilliseconds = finishDateTime.time - startDateTime.time
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMilliseconds)
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return formatHoursAndMinutes(hours.toInt(), remainingMinutes.toInt(), divider)
    }

    fun getTimeDiffBetweenDate(startDateTime: Date, finishDateTime: Date): Double {
        val diffInMilliseconds = finishDateTime.time - startDateTime.time
        return TimeUnit.MILLISECONDS.toMinutes(diffInMilliseconds) / 60.0
    }

    fun getTimeDiffInSeconds(startDateTime: Date, finishDateTime: Date): Long {
        val diffInMilliseconds = finishDateTime.time - startDateTime.time
        return TimeUnit.MILLISECONDS.toSeconds(diffInMilliseconds)
    }

    fun createDateObject(year: Int, monthOfYear: Int, dayOfMonth: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.clear()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DATE, dayOfMonth)
        return calendar.time
    }

    fun createDateObject(hourOfDay: Int, minute: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.clear()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        return calendar.time
    }

    fun combineDateAndTime(date: Date?, time: Date?): Date? {
        if (date == null && time == null) {
            return null
        } else if (date == null) {
            return time
        } else if (time == null) {
            return date
        } else {
            val dateTime = Calendar.getInstance()
            val date1 = Calendar.getInstance()
            date1.time = date
            val time1 = Calendar.getInstance()
            time1.time = time
            dateTime.set(date1.get(Calendar.YEAR), date1.get(Calendar.MONTH), date1.get(Calendar.DATE),
                    time1.get(Calendar.HOUR_OF_DAY), time1.get(Calendar.MINUTE), time1.get(Calendar.SECOND))
            dateTime.set(Calendar.MILLISECOND, time1.get(Calendar.MILLISECOND))
            return dateTime.time
        }
    }

    fun convertMinutesToHoursAndMinutes(totalMinutes: Int, divider: String): String {
        val hours = totalMinutes / 60
        val minutes = totalMinutes - hours * 60
        return formatHoursAndMinutes(hours, minutes, divider)
    }

    private fun formatHoursAndMinutes(hours: Int, minutes: Int, divider: String): String {
        return String.format(Locale.getDefault(), "%d%s%02d", hours, divider, minutes)
    }

    fun convertFloatingTimeToHoursAndMin(time: Double, divider: String): String {
        val hoursAndMins = convertDoubleToHoursAndMin(time)
        return formatHoursAndMinutes(hoursAndMins[0], hoursAndMins[1], divider)
    }

    private fun convertDoubleToHoursAndMin(time: Double): IntArray {
        val hours = time.toInt()
        return intArrayOf(hours, (60 * (time - hours)).toInt())
    }

    fun convertDoubleToMin(time: Double, doRoundOff: Boolean): Int {
        val hours = time.toInt()
        val totalMinutes = 60 * (time - hours) + 60 * hours
        return (if (doRoundOff) Math.round(totalMinutes) else totalMinutes) as Int
    }

    fun convertHoursAndMinutesInDouble(hours: Int, minutes: Int): Double {
        val totalMinutes = hours * 60 + minutes
        return totalMinutes / 60.0
    }

    fun getGreatestDate(date1: Date?, date2: Date?): Date? {
        if (date1 == null && date2 == null) return null
        if (date1 == null) return date2
        if (date2 == null) return date1
        return if (date1.time > date2.time) date1 else date2
    }

    fun changeParserTimeZone(usrTz: TimeZone) {
        localTimeZoneCalendar.timeZone = usrTz
    }

    fun getAfterDecimalValue(input: Double): Double {
        var input = input
        input = java.lang.Double.parseDouble(DecimalFormat("##.##").format(input))
        return input
    }

    fun compareDateForDescendingOrder(lhs: Date?, rhs: Date?): Int {
        return if (lhs == null && rhs == null) {
            0
        } else if (lhs == null || rhs == null) {
            if (lhs == null) {
                1
            } else {
                -1
            }
        } else {
            rhs.compareTo(lhs)
        }
    }

    fun compareDateForAscendingOrder(lhs: Date?, rhs: Date?): Int {
        return if (lhs == null && rhs == null) {
            0
        } else if (lhs == null || rhs == null) {
            if (lhs == null) {
                -1
            } else {
                1
            }
        } else {
            lhs.compareTo(rhs)
        }
    }

    fun isToday(date: Date): Boolean {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        calendar.time = date
        return year == calendar.get(Calendar.YEAR) && dayOfYear == calendar.get(Calendar.DAY_OF_YEAR)
    }

    fun convertToString(date: Date?): String? {
        return if (date == null) {
            null
        } else dateTimeformatter.format(date)
    }

    fun convertToDate(date: String?): Date? {
        if (date == null) {
            return null
        }
        try {
            return DISPLAY_DATE_FORMATTER.parse(date.trim())
        } catch (e: ParseException) {
            e.printStackTrace()
            return null
        }

    }

    fun getLongToDate(milSec: Long): String {

        return DISPLAY_DATE_FORMATTER.format(Date(milSec))

    }
}
