package com.example.admin.mvpinitialprojectsetupkotlin.data.model

import com.example.admin.mvpinitialprojectsetupkotlin.utils.FileUtils
import java.io.File

class SelectedItemModel(var file: File?, var mimeType: String, var id: String?, var type: String?){
    var size: String? = null
        get() = FileUtils.getSizeFromFile(file!!.length())

}