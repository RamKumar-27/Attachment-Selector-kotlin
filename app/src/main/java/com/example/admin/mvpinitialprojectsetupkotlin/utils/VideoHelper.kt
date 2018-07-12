package com.example.admin.mvpinitialprojectsetupkotlin.utils

import android.content.Context
import android.content.CursorLoader
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.admin.mvpinitialprojectsetupkotlin.app.AppConstants
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel
import java.util.ArrayList

object VideoHelper {

    fun gettAllVideos(context: Context): List<ImageDataModel> {

        val allVideos = ArrayList<ImageDataModel>()

        allVideos.clear()
        var uri: Uri
        var cursor: Cursor?
        var column_index_data: Int
        var column_index_folder_name: Int
        var column_index_duration: Int

        var absolutePathOfImage: String? = null
        var imageName: String
        var duration: String
        var id: String
        var imgId: Int

        //get all images from external storage

        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.VideoColumns.DURATION)
        val cursorLoader = CursorLoader(context, uri, projection, null, null,
                MediaStore.Images.Media.DATE_TAKEN + " DESC")

        //    cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        cursor = cursorLoader.loadInBackground()
        //        cursor = context.getContentResolver().query(uri, projection, null, null, null);

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)

        column_index_duration = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION)


        imgId = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)

        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data)

            imageName = cursor.getString(column_index_folder_name)

            duration = cursor.getString(column_index_duration)

            id = cursor.getString(imgId)


            var model = ImageDataModel(imageName, absolutePathOfImage, id, AppConstants.TYPE_VIDEO)
            model.duration = duration
            allVideos.add(model)
        }

        // Get all Internal storage Videos

        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        cursor = context.contentResolver.query(uri, projection, null, null, null)

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)

        column_index_duration = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION)

        imgId = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)

        while (cursor.moveToNext()) {

            id = cursor.getString(imgId)

            absolutePathOfImage = cursor.getString(column_index_data)

            imageName = cursor.getString(column_index_folder_name)

            duration = cursor.getString(column_index_duration)

            var model = ImageDataModel(imageName, absolutePathOfImage, id, AppConstants.TYPE_VIDEO)
            model.duration = duration
            allVideos.add(model)
        }

        return allVideos
    }

}