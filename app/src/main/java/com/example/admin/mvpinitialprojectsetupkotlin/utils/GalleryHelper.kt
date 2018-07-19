package com.example.admin.mvpinitialprojectsetupkotlin.utils

import android.app.Activity
import android.content.Context
import android.content.CursorLoader
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.admin.mvpinitialprojectsetupkotlin.app.AppConstants
import com.example.admin.mvpinitialprojectsetupkotlin.data.model.FolderItem


import com.example.admin.mvpinitialprojectsetupkotlin.data.model.ImageDataModel

import java.io.File
import java.util.HashMap
import java.util.TreeSet
import kotlin.collections.ArrayList

object GalleryHelper {


    fun getFromSdcard(filePaths: MutableList<String>) {
        val file = File(android.os.Environment.getExternalStorageDirectory(), "DCIM")

        if (file.isDirectory) {
            val listFile = file.listFiles()

            for (i in listFile.indices) {

                filePaths.add(listFile[i].absolutePath)
            }
        }
    }

    fun getFilePaths(activity: Activity): ArrayList<String> {

        val u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA)
        var c: Cursor? = null
        val dirList = TreeSet<String>()
        val resultIAV = ArrayList<String>()

        val directories = ArrayList<String>()
        if (u != null) {
            c = activity.contentResolver.query(u, projection, null, null, null)
        }

        if (c != null && c.moveToFirst()) {
            do {
                var tempDir = c.getString(0)
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"))
                try {
                    dirList.add(tempDir)
                } catch (e: Exception) {

                }
            } while (c.moveToNext())
            directories.addAll(dirList)
            dirList.toTypedArray()
        }

        for (i in dirList.indices) {
            val imageDir = File(directories.get(i))
            var imageList: Array<File>? = imageDir.listFiles() ?: continue
            for (imagePath in imageList!!) {
                try {

                    if (imagePath.isDirectory) {
                        imageList = imagePath.listFiles()
                    }
                    if (imagePath.name.contains(".jpg")
                            || imagePath.name.contains(".JPG")
                            || imagePath.name.contains(".jpeg")
                            || imagePath.name.contains(".JPEG")
                            || imagePath.name.contains(".png")
                            || imagePath.name.contains(".PNG")
                            || imagePath.name.contains(".gif")
                            || imagePath.name.contains(".GIF")
                            || imagePath.name.contains(".bmp")
                            || imagePath.name.contains(".BMP")) {

                        val path = imagePath.absolutePath
                        resultIAV.add(path)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                //  }
            }
        }

        return resultIAV
    }


    var imageFolderMap: MutableMap<String, ArrayList<ImageDataModel>> = HashMap()

    lateinit var keyList: ArrayList<String>

    var allImages = ArrayList<ImageDataModel>()

    /**
     * Getting All Images Path.
     *
     * @param activity the activity
     * @return ArrayList with images Path
     */
//    fun getImageFolderMap(activity: Activity): Map<String, ArrayList<ImageDataModel>> {
//
//        imageFolderMap.clear()
//
//        var uri: Uri
//        var cursor: Cursor?
//        var column_index_data: Int
//        var column_index_folder_name: Int
//
//        var absolutePathOfImage: String? = null
//        var folderName: String
//        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//
//        val projection = arrayOf(MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
//
//        cursor = activity.contentResolver.query(uri, projection, null, null, null)
//
//        column_index_data = cursor!!.getColumnIndexOrThrow(MediaColumns.DATA)
//
//        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
//
//        while (cursor.moveToNext()) {
//
//            absolutePathOfImage = cursor.getString(column_index_data)
//
//            folderName = cursor.getString(column_index_folder_name)
//
//            val imDataModel = ImageDataModel(folderName, absolutePathOfImage, imgId)
//
//            if (imageFolderMap.containsKey(folderName)) {
//
//                imageFolderMap[folderName]!!.add(imDataModel)
//            } else {
//
//                val listOfAllImages = ArrayList<ImageDataModel>()
//
//                listOfAllImages.add(imDataModel)
//
//                imageFolderMap[folderName] = listOfAllImages
//            }
//        }
//
//        // Get all Internal images
//        uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
//
//        cursor = activity.contentResolver.query(uri, projection, null, null, null)
//
//        column_index_data = cursor!!.getColumnIndexOrThrow(MediaColumns.DATA)
//
//        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
//
//        while (cursor.moveToNext()) {
//
//            absolutePathOfImage = cursor.getString(column_index_data)
//
//            folderName = cursor.getString(column_index_folder_name)
//
//            val imDataModel = ImageDataModel(folderName, absolutePathOfImage, imgId)
//
//            if (imageFolderMap.containsKey(folderName)) {
//
//                imageFolderMap[folderName]!!.add(imDataModel)
//            } else {
//
//                val listOfAllImages = ArrayList<ImageDataModel>()
//
//                listOfAllImages.add(imDataModel)
//
//                imageFolderMap[folderName] = listOfAllImages
//            }
//        }
//
//        keyList = ArrayList(imageFolderMap.keys)
//
//        return imageFolderMap
//    }

    /**
     * Getting All Images Path.
     *
     * @param context the activity
     * @return ArrayList with images Path
     */
    fun gettAllImages(context: Context, selectedId: ArrayList<String>): List<ImageDataModel> {

        //Remove older images to avoid copying same image twice

        allImages.clear()
        var uri: Uri
        var cursor: Cursor?
        var column_index_data: Int
        var column_index_folder_name: Int

        var absolutePathOfImage: String? = null
        var imageName: String
        var id: String
        var imgId: Int

        //get all images from external storage

        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media._ID)
        val cursorLoader = CursorLoader(context, uri, projection, null, null,
                MediaStore.Images.Media.DATE_TAKEN + " DESC")

        //    cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        cursor = cursorLoader.loadInBackground()
        //        cursor = context.getContentResolver().query(uri, projection, null, null, null);

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        imgId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data)

            imageName = cursor.getString(column_index_folder_name)

            id = cursor.getString(imgId)
            val model = ImageDataModel(imageName, absolutePathOfImage, id, AppConstants.TYPE_IMAMGE)
            if (selectedId.contains(id))
                model.selected = true
            allImages.add(model)
        }

        // Get all Internal storage images

        uri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI

        cursor = context.contentResolver.query(uri, projection, null, null, null)

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

        imgId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data)

            imageName = cursor.getString(column_index_folder_name)
            id = cursor.getString(imgId)

            val model = ImageDataModel(imageName, absolutePathOfImage, id, AppConstants.TYPE_IMAMGE)
            if (selectedId.contains(id))
                model.selected = true
            allImages.add(model)

        }

        return allImages
    }

    fun getImageFolders(context: Context): List<FolderItem> {

        /* For get Folder name from sdcrd & memory card*/
        val projection = arrayOf(MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
        val folderItemList = ArrayList<FolderItem>()
        val BUCKET_GROUP_BY = "1) GROUP BY 1,(2"
        val BUCKET_ORDER_BY = "MAX(datetaken) DESC"
        var cursor: Cursor?

        try {
//            val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
//                    BUCKET_GROUP_BY, null, BUCKET_ORDER_BY) ?: return folderItemList

            val cursorLoader = CursorLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY)

            cursor = cursorLoader.loadInBackground()

            if (cursor.moveToFirst()) {

                val bucketColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

                val idColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)
                val dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                folderItemList.clear()
                do {
                    // Get the field values
                    val folderName = cursor.getString(bucketColumn)
                    val id = cursor.getString(idColumn)
                    val folderIcon = cursor.getString(dataColumn)
                    /*For get particular id of each Folder*/
                    val folderId = MediaStore.Images.Media.BUCKET_ID + "=" + id
                    folderItemList.add(FolderItem(folderName, folderIcon, folderId))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return folderItemList;
    }

    fun getImagesFromFolderID(context: Context, folderID: String,
                              selectedId: ArrayList<String>): List<ImageDataModel> {

        val allImagesByFolder = ArrayList<ImageDataModel>()

        allImagesByFolder.clear()
        var uri: Uri
        var cursor: Cursor?
        var column_index_data: Int
        var column_index_folder_name: Int

        var absolutePathOfImage: String? = null
        var imageName: String
        var id: String
        var imgId: Int

        //get all images from external storage

        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media._ID)
        val cursorLoader = CursorLoader(context, uri, projection, folderID, null, MediaStore.Images.Media.DATE_TAKEN + " DESC")

        //    cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        cursor = cursorLoader.loadInBackground()
        //        cursor = context.getContentResolver().query(uri, projection, null, null, null);

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

        imgId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data)

            imageName = cursor.getString(column_index_folder_name)

            id = cursor.getString(imgId)

            val model = ImageDataModel(imageName, absolutePathOfImage, id, AppConstants.TYPE_IMAMGE)
            if (selectedId.contains(id))
                model.selected = true
            allImagesByFolder.add(model)
        }

        // Get all Internal storage images

        uri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI

        cursor = context.contentResolver.query(uri, projection, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC")

        column_index_data = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

        imgId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data)

            imageName = cursor.getString(column_index_folder_name)

            id = cursor.getString(imgId)

            val model = ImageDataModel(imageName, absolutePathOfImage, id, AppConstants.TYPE_IMAMGE)
            if (selectedId.contains(id))
                model.selected = true
            allImagesByFolder.add(model)
        }
        return allImagesByFolder
    }


}

