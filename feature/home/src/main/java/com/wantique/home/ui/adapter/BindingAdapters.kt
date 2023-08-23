package com.wantique.home.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.wantique.base.state.UiState
import com.wantique.base.state.isSuccessOrNull
import com.wantique.home.domain.model.Home

object BindingAdapters {
    @BindingAdapter("home")
    @JvmStatic
    fun setHome(view: RecyclerView, item: UiState<List<Home>>) {
        item.isSuccessOrNull()?.let {
            (view.adapter as HomeAdapter).submitList(it)
        }
    }

    @BindingAdapter("banner")
    @JvmStatic
    fun setBanner(view: ViewPager2, item: Home.Banner) {
        (view.adapter as BannerAdapter).submitList(item.banners)
    }

    @BindingAdapter("image")
    @JvmStatic
    fun setImage(view: ImageView, item: String) {
        Glide.with(view.context)
            .load(item)
            .into(view)
    }

    @BindingAdapter("updateCategoryPosition")
    @JvmStatic
    fun updateCategoryPosition(view: RecyclerView, item: UiState<Int>) {
        item.isSuccessOrNull()?.let {
            (view.adapter as HomeAdapter).getCategoryAdapter().updateCategoryPosition(it)
        }
    }

    @BindingAdapter("errorHandler")
    @JvmStatic
    fun handleError(view: ConstraintLayout, e: Throwable?) {
        e?.let {
            Toast.makeText(view.context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    @BindingAdapter("indicator")
    @JvmStatic
    fun setIndicator(view: ViewPager2, indicator: TextView) {
        indicator.text = "1 / ${view.adapter?.itemCount}"
        view.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                indicator.text = "${position + 1} / ${view.adapter?.itemCount}"
            }
        })
    }
}