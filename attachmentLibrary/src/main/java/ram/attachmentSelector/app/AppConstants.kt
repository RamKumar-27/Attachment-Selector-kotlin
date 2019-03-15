package ram.attachmentSelector.app

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

    const val FOLDER_ID = "FOLDER_ID"

    const val TYPE_IMAMGE = "IMAGE"
    const val TYPE_VIDEO = "VIDEO"
    const val TYPE_AUDIO = "AUDIO"
    const val TYPE_PDF = "PDF"
    const val TYPE_DOC = "DOC"
    const val TYPE_DOCX = "DOCX"

    /* Bundle keys*/
    const val IS_IMAGE_NEED = "IS_IMAGE_NEED"
    const val IS_VIDEO_NEED = "IS_VIDEO_NEED"
    const val IS_AUDIO_NEED = "IS_AUDIO_NEED"
    const val IS_PDF_NEED = "IS_PDF_NEED"
    const val IS_DOC_NEED = "IS_DOC_NEED"

}