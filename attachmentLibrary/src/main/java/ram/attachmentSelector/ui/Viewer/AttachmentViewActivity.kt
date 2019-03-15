package ram.attachmentSelector.ui.Viewer

import android.os.Bundle
import ram.attachmentSelector.R
import ram.attachmentSelector.base.BaseActivity

class AttachmentViewActivity : BaseActivity(){


    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.attachment_view)
    }
}