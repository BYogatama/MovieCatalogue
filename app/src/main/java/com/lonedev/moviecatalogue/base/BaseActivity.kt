package com.lonedev.moviecatalogue.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar
import com.lonedev.moviecatalogue.utils.Constant
import dagger.android.support.DaggerAppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

/**
 * Used for base of activity that extends DaggerAppCompatActivity, for every repeatable declaration in activity
 * can be put here, so it only declared once.
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    private lateinit var rootView: View

    @LayoutRes
    protected abstract fun layoutResource(): Int


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource())
        ButterKnife.bind(this)
        rootView = getRootView()
    }

    fun generateImageUrl(imagePath: String, imageWidth: String): String {
        return Constant.IMAGE_URL + imageWidth + imagePath
    }

    fun formatDate(date: String?): String {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        return formatter.format(parser.parse(date))
    }

    protected abstract fun getRootView() : View

    protected fun displaySnackBar(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }
}