package ram.attachmentSelector.data.model


import ram.attachmentSelector.utils.DateTimeUtils
import ram.attachmentSelector.utils.FileUtils
import ram.attachmentSelector.utils.JavaUtils
import java.io.File
import java.util.*

class ImageDataModel(var imageTitle: String?, var imagePath: String?,var  imgId: String,var  type: String) {


    var duration: String? = null
        get() = JavaUtils.milliSecondsToTimer(field!!.toLong())
    var file: File? = null
        get() = File(imagePath!!)
    var mimeType: String? = null
        get() = FileUtils.getMimeType(file!!.name)
    var size: String? = null
        get() = FileUtils.getSizeFromFile(file!!.length())
    var dateCreated: String? = null
        get() = DateTimeUtils.getLongToDate(file!!.lastModified())
    var selected: Boolean? = false

    fun getDate(): Date {
        return DateTimeUtils.convertToDate(dateCreated)!!
    }
}
