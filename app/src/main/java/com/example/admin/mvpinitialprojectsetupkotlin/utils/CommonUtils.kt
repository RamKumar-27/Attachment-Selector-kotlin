package com.example.admin.mvpinitialprojectsetupkotlin.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.text.InputFilter


import com.example.admin.mvpinitialprojectsetupkotlin.app.AppConstants

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.TimeZone

object CommonUtils {

    @Throws(IOException::class)
    fun saveBitmapToJPG(bitmap: Bitmap, photo: File) {
        val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        val stream = FileOutputStream(photo)
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        stream.close()
    }

    fun restartActivity(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

    fun dp2px(resources: Resources, dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    fun sp2px(resources: Resources, sp: Float): Float {
        val scale = resources.displayMetrics.scaledDensity
        return sp * scale
    }

    fun isAvailable(ctx: Context, intent: Intent): Boolean {
        val mgr = ctx.packageManager
        val list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return list.size > 0
    }

    fun changeApiRequestTimeZoneToUserProfileTimeZone(userTimeZone: String) {
        if (TextUtils.isEmpty(userTimeZone)) {
            return
        }
        val usrTz = TimeZone.getTimeZone(userTimeZone)
        AppConstants.changeApiRequestDateTimeFormatterTimeZone(usrTz)
        DateTimeUtils.changeParserTimeZone(usrTz)
        AppConstants.setUserProfileTimeZone(usrTz)
    }
}