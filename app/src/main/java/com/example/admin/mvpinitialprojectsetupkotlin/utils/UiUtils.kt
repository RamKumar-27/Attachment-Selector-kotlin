package com.example.admin.mvpinitialprojectsetupkotlin.utils

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast


object UiUtils {

    fun showSnackBar(view: View, message: String, length: Int) {
        val snackbar = Snackbar.make(view, message, length)
        val v = snackbar.view
        val textView = v.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.maxLines = 4
        snackbar.show()
    }

    fun showSnackBarWithAction(view: View, messageResId: Int, length: Int,
                               actionResId: Int, actionClickListener: View.OnClickListener) {
        val snackbar = Snackbar.make(view, messageResId, length)
        val v = snackbar.view
        val textView = v.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.maxLines = 4
        snackbar.setAction(actionResId, actionClickListener)
        snackbar.show()
    }

    fun showSnackBarWithAction(view: View, message: String, length: Int, action: String,
                               actionClickListener: View.OnClickListener) {
        val snackbar = Snackbar.make(view, message, length)
        val v = snackbar.view
        val textView = v.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.maxLines = 4
        snackbar.setAction(action, actionClickListener)
        snackbar.show()
    }

    fun showSnackBar(view: View, message: Int, length: Int) {
        val snackbar = Snackbar.make(view, message, length)
        val v = snackbar.view
        val textView = v.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.maxLines = 4
        snackbar.show()
    }

    fun showToast(mActivity: AppCompatActivity, message: String) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun createAlertDialogWithTwoButtons(context: Context, title: String,
                                        message: String, buttonFirstText: String, buttonSecondText: String,
                                        firstListener: DialogInterface.OnClickListener,
                                        secondListener: DialogInterface.OnClickListener): AlertDialog {
        val alertDialog = AlertDialog.Builder(context).setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonFirstText, firstListener)
                .setNegativeButton(buttonSecondText, secondListener)
                .create()
        if (TextUtils.isEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        return alertDialog
    }

    fun createAlertDialog(context: Context, title: String, message: String,
                          buttonText: String, onClickListener: DialogInterface.OnClickListener): AlertDialog {
        val alertDialog = AlertDialog.Builder(context).setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(buttonText, onClickListener)
                .create()
        if (TextUtils.isEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        return alertDialog
    }
}
