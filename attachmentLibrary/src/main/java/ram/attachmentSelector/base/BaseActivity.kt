package ram.attachmentSelector.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import ram.attachmentSelector.R


open class BaseActivity : AppCompatActivity() {
    private var dialog: Dialog? = null
    val visibleFragment: Fragment?
        get() {
            val fragmentManager = supportFragmentManager
            val fragments = fragmentManager.fragments
            if (fragments != null) {
                for (fragment in fragments) {
                    if (fragment != null && fragment.isVisible) return fragment
                }
            }
            return null
        }
//    private static final String TAG = LoggerUtils.makeLogTag(BaseActivity.class);

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun setHeaderTitle(title: String) {
        if (supportActionBar != null) supportActionBar!!.setTitle(title)
    }

    fun showBackButton(status: Boolean) {
        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(status)
    }

    fun hideSoftInput() {
        try {
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
        } catch (e: NullPointerException) {
            //            LoggerUtils.e(TAG, "Exception in hideSoftInput", e);
        }

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        var userEvent = false
        try {
            val v = currentFocus
            userEvent = super.dispatchTouchEvent(event)
            if (v is EditText) {
                val w = currentFocus
                val scr = IntArray(2)
                w?.getLocationOnScreen(scr)
                assert(w != null)
                val x = event.rawX + w!!.left - scr[0]
                val y = event.rawY + w.top - scr[1]
                if (event.action == MotionEvent.ACTION_UP && (x < w.left
                                || x >= w.right
                                || y < w.top
                                || y > w.bottom)) {
                    hideSoftInput()
                }
            }
        } catch (e: Exception) {
            //            LoggerUtils.e(TAG, "Exception in dispatchTouchEvent", e);
        }

        return userEvent
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun showProgress() {
        hideProgress()
        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.progress_dialog)
        dialog!!.setCancelable(false)
        dialog!!.show()
    }



    fun hideProgress() {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    fun setBundle(fragment: Fragment, bundle: Bundle): Fragment {
        fragment.arguments = bundle
        return fragment
    }

    fun setNewFragment(fragment: Fragment, title: String, addbackstack: Boolean) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.frame_content, fragment)
        if (addbackstack)
            transaction.addToBackStack(title)
        transaction.commit()

    }

}
