package ram.attachmentSelector.utils

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import ram.attachmentSelector.data.model.ImageDataModel
import java.util.*

object DocumentFileHelper {

    fun getDocumentListFromStorage(context: Context, extension: String, type: String): ArrayList<ImageDataModel> {
        val allfiles = ArrayList<ImageDataModel>()

        val pdfResolver = context.getContentResolver()
        val pdfUri = android.provider.MediaStore.Files.getContentUri("external")
        val selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE + "=?"
        val mimeTypePDF = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
//        val mimeTypeDOC = MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc")
        val selectionArgsPdf = arrayOf(mimeTypePDF)
        val pdfCursor = pdfResolver.query(pdfUri, null, selectionMimeType, selectionArgsPdf, null)
        if (pdfCursor != null && pdfCursor!!.moveToFirst()) {
            //get columns
            val pdfPath = pdfCursor!!.getColumnIndex(android.provider.MediaStore.Files.FileColumns.DATA)

            val column_index_folder_name = pdfCursor!!.getString(pdfCursor.getColumnIndex(android.provider.MediaStore.Files.FileColumns.DISPLAY_NAME))


            val imgId = pdfCursor!!.getString(pdfCursor!!.getColumnIndex(android.provider.MediaStore.Files.FileColumns._ID))
            //add songs to list
            do {
                val thisPath = pdfCursor!!.getString(pdfPath)

                var model = ImageDataModel(column_index_folder_name, thisPath, imgId, type)
                allfiles.add(model)
                Log.e(extension, thisPath)
            } while (pdfCursor!!.moveToNext())
        }

        val pdfInternalUri = android.provider.MediaStore.Files.getContentUri("internal")
//        val mimeTypeDOC = MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc")
        val pdfInternalCursor = pdfResolver.query(pdfInternalUri, null, selectionMimeType, selectionArgsPdf, null)
        if (pdfInternalCursor != null && pdfInternalCursor!!.moveToFirst()) {
            //get columns
            val pdfPath = pdfInternalCursor!!.getColumnIndex(android.provider.MediaStore.Files.FileColumns.DATA)

            val column_index_folder_name = pdfInternalCursor!!.getString(pdfInternalCursor.getColumnIndex(android.provider.MediaStore.Files.FileColumns.DISPLAY_NAME))


            val imgId = pdfInternalCursor!!.getString(pdfInternalCursor!!.getColumnIndex(android.provider.MediaStore.Files.FileColumns._ID))
            //add songs to list
            do {
                val thisPath = pdfInternalCursor!!.getString(pdfPath)

                var model = ImageDataModel(column_index_folder_name, thisPath, imgId, type)
                allfiles.add(model)
                Log.e(extension, thisPath)
            } while (pdfInternalCursor!!.moveToNext())
        }
        return allfiles
    }


}