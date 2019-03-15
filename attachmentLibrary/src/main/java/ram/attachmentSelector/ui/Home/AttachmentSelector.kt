package ram.attachmentSelector.ui.Home

import android.content.Context
import ram.attachmentSelector.base.AttachmentSelectedListener

class AttachmentSelector(val context: Context) {


    private var isImages: Boolean? = false
    private var isVideo: Boolean? = false
    private var isAudio: Boolean? = false
    private var isPDF: Boolean? = false
    private var isDoc: Boolean? = false

    /* navigate to attachment selection activity by user requirement*/
    fun start(listener: AttachmentSelectedListener) {
       HomeActivity.getCallingIntent(context,isImages!!,isVideo!!,isAudio!!,isPDF!!,isDoc!!,listener)
    }

    /**
     *  @param isNeed can enable or disable the image List
     *  by default is should be false
     **/
    fun isImagesNeed(isNeed: Boolean): AttachmentSelector {
        isImages = isNeed
        return this
    }

    /**
     *  @param isNeed can enable or disable the Video List
     *  by default is should be false
     **/
    fun isVideoNeed(isNeed: Boolean): AttachmentSelector {
        isVideo = isNeed
        return this
    }

    /**
     *  @param isNeed can enable or disable the Audio List
     *  by default is should be false
     **/
    fun isAudioNeed(isNeed: Boolean): AttachmentSelector {
        isAudio = isNeed
        return this
    }

    /**
     *  @param isNeed can enable or disable the PDF List
     *  by default is should be false
     **/
    fun isPDFNeed(isNeed: Boolean): AttachmentSelector {
        isPDF = isNeed
        return this
    }

    /**
     *  @param isNeed can enable or disable the doc List
     *  by default is should be false
     **/
    fun isDocNeed(isNeed: Boolean): AttachmentSelector {
        isDoc = isNeed
        return this
    }
}