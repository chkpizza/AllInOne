package com.wantique.mypage.ui.oss.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.mypage.databinding.ListItemOssLicenseBinding
import com.wantique.mypage.domain.model.OssLicense

class OssLicenseAdapter(
    private val onOssLicenseClickListener: OnOssLicenseClickListener
) : ListAdapter<OssLicense, OssLicenseAdapter.OssLicenseViewHolder>(object : DiffUtil.ItemCallback<OssLicense>() {
    override fun areItemsTheSame(oldItem: OssLicense, newItem: OssLicense): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: OssLicense, newItem: OssLicense): Boolean {
        return oldItem == newItem
    }

}) {
    inner class OssLicenseViewHolder(private val binding: ListItemOssLicenseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OssLicense) {
            binding.ossLicenseTvName.text = item.name

            binding.root.setOnClickListener {
                onOssLicenseClickListener.onClick(item.url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OssLicenseViewHolder {
        val binding = ListItemOssLicenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OssLicenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OssLicenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}