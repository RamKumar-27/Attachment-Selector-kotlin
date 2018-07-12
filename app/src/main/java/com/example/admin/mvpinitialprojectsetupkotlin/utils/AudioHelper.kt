package com.example.admin.mvpinitialprojectsetupkotlin.utils

import android.content.Context
import android.content.CursorLoader
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.admin.mvpinitialprojectsetupkotlin.app.AppConstants
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel
import java.util.ArrayList

object AudioHelper {

    fun getAllAudio(context: Context): List<ImageDataModel> {

        val allVideos = ArrayList<ImageDataModel>()

        allVideos.clear()
        var uri: Uri
        var cursor: Cursor?
        var column_index_data: Int
        var column_index_folder_name: Int
        var column_index_duration: Int

        var absolutePathOfImage: String? = null
        var imageName: String
        var id: String
        var duration: String? = null
        var imgId: Int

        //get all images from external storage

        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.DISPLAY_NAME, MediaStore.Audio.AudioColumns.DURATION, MediaStore.Audio.Media._ID)
        val cursorLoader = CursorLoader(context, uri, projection, null, null,
                MediaStore.Audio.Media.DATE_MODIFIED + " DESC")

        //    cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        cursor = cursorLoader.loadInBackground()
        //        cursor = context.getContentResolver().query(uri, projection, null, null, null);

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)

        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)

        column_index_duration = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)

        imgId = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)

        while (cursor.moveToNext()) {

            id = cursor.getString(imgId)

            absolutePathOfImage = cursor.getString(column_index_data)

            imageName = cursor.getString(column_index_folder_name)

            if (cursor.getString(column_index_duration) != null)
                duration = cursor.getString(column_index_duration)

            var model = ImageDataModel(imageName, absolutePathOfImage, id, AppConstants.TYPE_AUDIO)
            model.duration = duration
            allVideos.add(model)
        }

        // Get all Internal storage Videos

        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        cursor = context.contentResolver.query(uri, projection, null, null, null)

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)

        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)

        column_index_duration = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)

        imgId = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)

        while (cursor.moveToNext()) {

            id = cursor.getString(imgId)

            absolutePathOfImage = cursor.getString(column_index_data)

            imageName = cursor.getString(column_index_folder_name)

            if (cursor.getString(column_index_duration) != null)
                duration = cursor.getString(column_index_duration)

            var model = ImageDataModel(imageName, absolutePathOfImage, id, AppConstants.TYPE_AUDIO)
            model.duration = duration
            allVideos.add(model)
        }

        return allVideos
    }

}