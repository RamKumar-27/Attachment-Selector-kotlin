package ram.attachmentSelector.base

import ram.attachmentSelector.data.model.SelectedItemModel

interface AttachmentSelectedListener {
    fun onSelectedAttachments(list: ArrayList<SelectedItemModel>)
}