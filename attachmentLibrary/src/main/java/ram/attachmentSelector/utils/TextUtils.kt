package ram.attachmentSelector.utils

import android.util.Base64

import java.io.UnsupportedEncodingException
import java.util.UUID


object TextUtils {

    fun isEmpty(str: String?): Boolean {
        return str == null || str.trim { it <= ' ' }.length == 0
    }

    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.toString().trim { it <= ' ' }.isEmpty()
    }

    /**
     * this will prettify the input string.
     * If the string is null then it will return empty string else It will trim the string and return
     * it.
     *
     * @param str string to be prettified for display.
     * @return If the string is null then it will return empty string else It will trim the string and
     * return it.
     */
    fun displayPretty(str: String?): String {
        return str?.trim { it <= ' ' } ?: ""
    }

    fun combineStrings(strings: List<String>?, combiningCharacter: String): String {
        if (strings == null || strings.isEmpty()) {
            return ""
        }
        val queryBuilder = StringBuilder(strings[0])
        var i = 1
        val stringsSize = strings.size
        while (i < stringsSize) {
            val s = strings[i]
            queryBuilder.append(combiningCharacter).append(s)
            i++
        }

        return queryBuilder.toString()
    }

    @Throws(UnsupportedEncodingException::class)
    fun getBase64EncodeData(input: String): String {
        val data = input.toByteArray(charset("UTF-8"))
        return Base64.encodeToString(data, Base64.NO_WRAP)
    }

    fun compareStringsForDescendingOrder(str1: String?, str2: String?): Int {
        return if (str1 == null && str2 == null) {
            0
        } else if (str1 == null || str2 == null) {
            if (str1 == null) {
                1
            } else {
                -1
            }
        } else {
            str1.compareTo(str2)
        }
    }

    fun generateUniqueClientId(): String {
        return UUID.randomUUID().toString()
    }
}
