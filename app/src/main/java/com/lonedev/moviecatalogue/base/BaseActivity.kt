package com.lonedev.moviecatalogue.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import butterknife.ButterKnife
import dagger.android.support.DaggerAppCompatActivity

/**
 * Used for base of activity that extends DaggerAppCompatActivity, for every repeatable declaration in activity
 * can be put here, so it only declared once.
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    @LayoutRes
    protected abstract fun layoutResource(): Int

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource())
        ButterKnife.bind(this)
    }

}