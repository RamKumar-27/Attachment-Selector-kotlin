package com.example.admin.mvpinitialprojectsetupkotlin.data.model


import com.example.admin.mvpinitialprojectsetupkotlin.utils.DateTimeUtils
import com.example.admin.mvpinitialprojectsetupkotlin.utils.FileUtils
import com.example.admin.mvpinitialprojectsetupkotlin.utils.JavaUtils

import java.io.File
import java.util.*

class ImageDataModel(var imageTitle: String?, var imagePath: String?,var  imgId: String,var  type: String) {


    var duration: String? = null
        get() = JavaUtils.milliSecondsToTimer(field!!.toLong())
    var file: File? = null
        get() = File(imagePath!!)
    var mimeType: String? = null
        get() = FileUtils.getMimeType(imageTitle!!)
    var size: String? = null
        get() = FileUtils.getSizeFromFile(file!!.length())
    var dateCreated: String? = null
        get() = DateTimeUtils.getLongToDate(file!!.lastModified())
    var selected: Boolean? = false

    fun getDate(): Date {
        return DateTimeUtils.convertToDate(dateCreated)!!
    }
}
