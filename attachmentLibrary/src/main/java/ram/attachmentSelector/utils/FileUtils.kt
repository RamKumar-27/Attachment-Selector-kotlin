package ram.attachmentSelector.utils

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.DatabaseUtils
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.webkit.MimeTypeMap
import java.io.*
import java.text.DecimalFormat
import java.util.*


object FileUtils {

    val MIME_TYPE_AUDIO = "audio/*"
    val MIME_TYPE_TEXT = "text/*"
    val MIME_TYPE_IMAGE = "image/*"
    val MIME_TYPE_VIDEO = "video/*"
    val MIME_TYPE_APP = "application/*"
    val HIDDEN_PREFIX = "."
    /**
     * TAG for log messages.
     */
    internal val TAG = "FileUtils"
    private val DEBUG = false // Set to true to enable logging
    /**
     * File and folder comparator. TODO Expose sorting option method
     *
     * @author paulburke
     */
    var sComparator: Comparator<File> = Comparator { f1, f2 ->
        // Sort alphabetically by lower case, which is much cleaner
        f1.name.toLowerCase().compareTo(f2.name.toLowerCase())
    }
    /**
     * File (not directories) filter.
     *
     * @author paulburke
     */
    var sFileFilter: FileFilter = FileFilter { file ->
        val fileName = file.name
        // Return files only (not directories) and skip hidden files
        file.isFile && !fileName.startsWith(HIDDEN_PREFIX)
    }
    /**
     * Folder (directories) filter.
     *
     * @author paulburke
     */
    var sDirFilter: FileFilter = FileFilter { file ->
        val fileName = file.name
        // Return directories only and skip hidden directories
        file.isDirectory && !fileName.startsWith(HIDDEN_PREFIX)
    }

    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @return Extension including the dot("."); "" if there is no extension;
     * null if uri was null.
     */
    fun getExtension(uri: String?): String? {
        if (uri == null) {
            return null
        }

        val dot = uri.lastIndexOf(".")
        return if (dot >= 0) {
            uri.substring(dot)
        } else {
            // No extension.
            ""
        }
    }

    fun getExtensionFromMimeType(mimeType: String?): String? {
        return if (mimeType == null) {
            null
        } else MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)

    }

    fun getExtensionFromUrl(url: String?): String? {
        return if (url == null) {
            null
        } else getExtensionFromMimeType(getMimeTypeFromUrl(url))

    }

    /**
     * @return Whether the URI is a local one.
     */
    fun isLocal(url: String?): Boolean {
        return url != null && !url.startsWith("http://") && !url.startsWith("https://")
    }

    /**
     * @return True if Uri is a MediaStore Uri.
     * @author paulburke
     */
    fun isMediaUri(uri: Uri): Boolean {
        return "media".equals(uri.authority, ignoreCase = true)
    }

    /**
     * Convert File into Uri.
     *
     * @return uri
     */
    fun getUri(file: File?): Uri? {
        return if (file != null) {
            Uri.fromFile(file)
        } else null
    }

    /**
     * Returns the path only (without file name).
     */
    fun getPathWithoutFilename(file: File?): File? {
        if (file != null) {
            if (file.isDirectory) {
                // no file to be split off. Return everything
                return file
            } else {
                val filename = file.name
                val filepath = file.absolutePath

                // Construct path without file name.
                var pathwithoutname = filepath.substring(0, filepath.length - filename.length)
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0, pathwithoutname.length - 1)
                }
                return File(pathwithoutname)
            }
        }
        return null
    }

    /**
     * @return The MIME type for the given file.
     */
    fun getMimeType(file: File): String? {

        val extension = getExtension(file.name)

        return if (extension!!.length > 0) {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1))
        } else "application/octet-stream"

    }

    /**
     * @return The MIME type for the give Uri.
     */
    fun getMimeType(context: Context, uri: Uri): String? {
        val mimeType: String?
        if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val cr = context.contentResolver
            mimeType = cr.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase())
        }
        return mimeType
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     * @author paulburke
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     * @author paulburke
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     * @author paulburke
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     * @author paulburke
     */
    fun getDataColumn(context: Context, uri: Uri, selection: String,
                      selectionArgs: Array<String>): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                if (DEBUG) DatabaseUtils.dumpCursor(cursor)

                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    fun getFileSizeWithCursor(context: Context, uri: Uri?): String {

        if (uri == null) return "0 KB"

        val returnCursor = context.contentResolver.query(uri, null, null, null, null)

        if (returnCursor != null) {
            val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
            returnCursor.moveToFirst()
            if (sizeIndex != 0) {
                val size = returnCursor.getLong(sizeIndex)
                returnCursor.close()
                return getStringSizeLengthFile(size)
            }
        }

        return "0 KB"
    }

    fun getStringSizeLengthFile(size: Long): String {

        val df = DecimalFormat("0.00")

        val sizeKb = 1024.0f
        val sizeMo = sizeKb * sizeKb
        val sizeGo = sizeMo * sizeKb
        val sizeTerra = sizeGo * sizeKb
        if (size < sizeMo) {
            return df.format((size / sizeKb).toDouble()) + " KB"
        } else if (size < sizeGo) {
            return df.format((size / sizeMo).toDouble()) + " MB"
        } else if (size < sizeTerra) return df.format((size / sizeGo).toDouble()) + " GB"

        return ""
    }

    /**
     * Get the file size in a human-readable string.
     *
     * @author paulburke
     */
    fun getReadableFileSize(size: Int): String {

        val BYTES_IN_KILOBYTES = 1024
        val dec = DecimalFormat("###.#")
        val KILOBYTES = " KB"
        val MEGABYTES = " MB"
        val GIGABYTES = " GB"
        var fileSize = 0f
        var suffix = KILOBYTES

        if (size > BYTES_IN_KILOBYTES) {
            fileSize = (size / BYTES_IN_KILOBYTES).toFloat()
            if (fileSize > BYTES_IN_KILOBYTES) {
                fileSize = fileSize / BYTES_IN_KILOBYTES
                if (fileSize > BYTES_IN_KILOBYTES) {
                    fileSize = fileSize / BYTES_IN_KILOBYTES
                    suffix = GIGABYTES
                } else {
                    suffix = MEGABYTES
                }
            }
        }
        return dec.format(fileSize.toDouble()) + suffix
    }

    /**
     * Attempt to retrieve the thumbnail of given File from the MediaStore. This
     * should not be called on the UI thread.
     *
     * @author paulburke
     */
    fun getThumbnail(context: Context, file: File): Bitmap? {
        return getThumbnail(context, getUri(file), getMimeType(file))
    }

    /**
     * Attempt to retrieve the thumbnail of given Uri from the MediaStore. This
     * should not be called on the UI thread.
     *
     * @author paulburke
     */
    @JvmOverloads
    fun getThumbnail(context: Context, uri: Uri?, mimeType: String? = getMimeType(context, uri!!)): Bitmap? {
        if (!isMediaUri(uri!!)) {
            return null
        }

        var bm: Bitmap? = null
        if (uri != null) {
            val resolver = context.contentResolver
            var cursor: Cursor? = null
            try {
                cursor = resolver.query(uri, null, null, null, null)
                if (cursor!!.moveToFirst()) {
                    val id = cursor.getInt(0)
                    if (DEBUG) Log.d(TAG, "Got thumb ID: $id")

                    if (mimeType!!.contains("video")) {
                        bm = MediaStore.Video.Thumbnails.getThumbnail(resolver, id.toLong(),
                                MediaStore.Video.Thumbnails.MINI_KIND, null)
                    } else if (mimeType.contains(FileUtils.MIME_TYPE_IMAGE)) {
                        bm = MediaStore.Images.Thumbnails.getThumbnail(resolver, id.toLong(),
                                MediaStore.Images.Thumbnails.MINI_KIND, null)
                    }
                }
            } catch (e: Exception) {
            } finally {
                cursor?.close()
            }
        }
        return bm
    }

    /**
     * Get the Intent for selecting content to be used in an Intent Chooser.
     *
     * @return The intent for opening a file with Intent.createChooser()
     * @author paulburke
     */
    fun createGetContentIntent(): Intent {
        // Implicitly allow the user to select a particular kind of data
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        // The MIME data type filter
        intent.type = "*/*"
        // Only return URIs that can be opened with ContentResolver
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        return intent
    }

    fun getDownloadFileDirectory(context: Context): File {
        return context.cacheDir
    }

    /**
     * @return The MIME type for the give Url.
     */
    fun getMimeTypeFromUrl(url: String): String? {
        return MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url))
    }

    fun copyFile(context: Context, src: Uri, dst: File): Boolean {
        var inputStream: InputStream? = null
        var fileOutputStream: FileOutputStream? = null
        try {
            inputStream = context.contentResolver.openInputStream(src)
            fileOutputStream = FileOutputStream(dst)

            if (!dst.exists()) {
                dst.createNewFile()
            }

            val buffer = ByteArray(1000)
            while (inputStream!!.read(buffer, 0, buffer.size) >= 0) {
                fileOutputStream.write(buffer, 0, buffer.size)
            }
            return true
        } catch (e: IOException) {
            return false
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    fun getSizeFromFile(size: Long): String {
        var hrSize: String? = null

        val b = size.toDouble()
        val k = size / 1024.0
        val m = size / 1024.0 / 1024.0
        val g = size / 1024.0 / 1024.0 / 1024.0
        val t = size / 1024.0 / 1024.0 / 1024.0 / 1024.0

        val dec = DecimalFormat("0.00")

        if (t > 1) {
            hrSize = dec.format(t) + " TB"
        } else if (g > 1) {
            hrSize = dec.format(g) + " GB"
        } else if (m > 1) {
            hrSize = dec.format(m) + " MB"
        } else if (k > 1) {
            hrSize = dec.format(k) + " KB"
        } else {
            hrSize = dec.format(b) + " Bytes"
        }

        return hrSize
    }

    fun getFileName(context: Context, uri: Uri): String? {
        var fileName: String? = null
        if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val cursor = context.contentResolver.query(uri, null, null, null, null, null)

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    fileName = cursor.getString(index)
                }
                cursor.close()
            }
        } else {
            fileName = uri.lastPathSegment
        }
        return fileName
    }

    fun getFileExtension(filePath: String): String {
        if (TextUtils.isEmpty(filePath)) {
            return ""
        }
        var extension = ""
        val pos = filePath.lastIndexOf('.')
        if (pos != -1) {
            extension = filePath.substring(filePath.lastIndexOf('.') + 1,
                    filePath.length)
            return extension
        } else
            return "*/*"
    }


    fun getMimeType(fileUrl: String): String {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(getFileExtension(fileUrl))
    }

}