package com.example.admin.sample.ui.Home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.PermissionChecker
import android.widget.Toast
import com.example.admin.sample.R
import kotlinx.android.synthetic.main.layout_main.*
import ram.attachmentSelector.base.AttachmentSelectedListener
import ram.attachmentSelector.base.BaseActivity
import ram.attachmentSelector.data.model.SelectedItemModel
import ram.attachmentSelector.ui.Home.AttachmentSelector

class MainActivity : BaseActivity(), AttachmentSelectedListener {
    private val PERMISSIONS_REQUEST_STORAGE = 2314

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.layout_main)
        button.setOnClickListener { checkCameraPermission() }
    }


    override fun onSelectedAttachments(list: ArrayList<SelectedItemModel>) {
        Toast.makeText(this, list.size.toString() + " Media selected", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_STORAGE) {
            if (permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && permissions[1] == Manifest.permission.WRITE_EXTERNAL_STORAGE && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                openUI()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openUI() {
        AttachmentSelector(this).isImagesNeed(true)
                .isVideoNeed(true).isPDFNeed(true)
                .isAudioNeed(true).isDocNeed(true).start(this)
    }

    private fun checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED || PermissionChecker.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_STORAGE
                )
                return
            }
        }
        openUI()

    }

}
