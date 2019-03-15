package ram.attachmentSelector.app

import android.content.Context
import ram.attachmentSelector.base.MainThreadBus

class AttachmentApplication {

    companion object {
        var appController: AttachmentApplication? = null

        fun getInstanse(): AttachmentApplication? {
            return appController
        }
    }
    private var bus: MainThreadBus? = null


   fun init(context: Context){
       appController = this
       bus = MainThreadBus()
   }
    fun getBus(): MainThreadBus {
        return bus!!
    }


}