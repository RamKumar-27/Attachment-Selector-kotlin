package ram.attachmentSelector.utils

import android.util.Log
import ram.attachmentSelector.BuildConfig


object LoggerUtils {

    private val LOG_PREFIX = "inv_"
    private val LOG_PREFIX_LENGTH = LOG_PREFIX.length
    private val MAX_LOG_TAG_LENGTH = 23

    /**
     * Make log tag.
     *
     * @param str the str
     * @return the string
     */
    fun makeLogTag(str: String): String {
        return if (str.length > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1)
        } else LOG_PREFIX + str
    }

    /**
     * Don't use this when obfuscating class names!
     */
    fun makeLogTag(cls: Class<*>): String {
        return makeLogTag(cls.simpleName)
    }

    /**
     * D void.
     *
     * @param tag the tag
     * @param message the message
     */
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    /**
     * D void.
     *
     * @param tag the tag
     * @param cause the cause
     */
    fun d(tag: String, cause: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, tag, cause)
        }
    }

    /**
     * V void.
     *
     * @param tag the tag
     * @param message the message
     */
    fun v(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }

    /**
     * V void.
     *
     * @param tag the tag
     * @param message the message
     * @param cause the cause
     */
    fun v(tag: String, message: String, cause: Throwable) {
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message, cause)
        }
    }

    /**
     * I void.
     *
     * @param tag the tag
     * @param message the message
     */
    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    /**
     * I void.
     *
     * @param tag the tag
     * @param cause the cause
     */
    fun i(tag: String, cause: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, tag, cause)
        }
    }

    /**
     * W void.
     *
     * @param tag the tag
     * @param message the message
     */
    fun w(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message)
        }
    }

    /**
     * W void.
     *
     * @param tag the tag
     * @param cause the cause
     */
    fun w(tag: String, cause: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, tag, cause)
        }
    }

    /**
     * E void.
     *
     * @param tag the tag
     * @param message the message
     */
    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

    /**
     * E void.
     *
     * @param tag the tag
     * @param cause the cause
     */
    fun e(tag: String, cause: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, tag, cause)
        }
    }

    /**
     * E void
     */
    fun e(tag: String, message: String, cause: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, cause)
        }
    }

    /**
     * To log unhandled exception
     */
    fun logUnExpectedException(throwable: Throwable) {
        throwable.printStackTrace()
        //if (Fabric.isInitialized()) {
        //  Crashlytics.logException(throwable);
        //}
    }
}//Default constructor.
