package com.wantique.home.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.wantique.base.state.UiState
import com.wantique.base.state.isSuccessOrNull
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.model.ProfessorItem

object HomeBindingAdapter {
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

    @BindingAdapter("belong")
    @JvmStatic
    fun setBelong(view: TextView, item: ProfessorItem) {
        view.text = item.belong

        when(item.belong) {
            "메가" -> view.setTextColor(ResourcesCompat.getColor(view.context.resources, com.wantique.resource.R.color.megaTextColor, null))
            "공단기" -> view.setTextColor(ResourcesCompat.getColor(view.context.resources, com.wantique.resource.R.color.dangiTextColor, null))
        }
    }

    @BindingAdapter("name")
    @JvmStatic
    fun setName(view: TextView, item: ProfessorItem) {
        view.text = "${item.name} 교수님"
    }

    @BindingAdapter("mask")
    @JvmStatic
    fun setMask(view: TextView, item: ProfessorItem) {
        view.isVisible = !item.update
    }

    /*
    @BindingAdapter("errorHandler")
    @JvmStatic
    fun handleError(view: ConstraintLayout, e: Throwable?) {
        e?.let {
            if(it.message == "NETWORK_CONNECTION_ERROR") {
                view.isVisible = true
            } else {
                Toast.makeText(view.context, it.message, Toast.LENGTH_SHORT).show()
                view.isVisible = false
            }
        } ?: run {
            view.isVisible = false
        }
    }

     */

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