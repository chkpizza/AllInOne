package com.wantique.mypage.ui.oss

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wantique.base.ui.BaseFragment
import com.wantique.mypage.R
import com.wantique.mypage.databinding.FragmentOssLicenseBinding
import com.wantique.mypage.domain.model.OssLicense
import com.wantique.mypage.ui.oss.adapter.OnOssLicenseClickListener
import com.wantique.mypage.ui.oss.adapter.OssLicenseAdapter

class OssLicenseFragment : BaseFragment<FragmentOssLicenseBinding>(R.layout.fragment_oss_license) {
    private lateinit var ossLicenseAdapter: OssLicenseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateInsets()
        setUpViewListener()
        setUpRecyclerView()
    }

    private fun setUpViewListener() {
        binding.ossLicenseToolbar.setNavigationOnClickListener {
            navigator.navigateUp()
        }
    }

    private fun setUpRecyclerView() {
        val licenseName = resources.getStringArray(com.wantique.resource.R.array.oss_license_name)
        val licenseUrl = resources.getStringArray(com.wantique.resource.R.array.oss_license_url)
        val license = licenseName.zip(licenseUrl).map { (name, url) -> OssLicense(name, url) }

        val onOssLicenseClickListener = object : OnOssLicenseClickListener {
            override fun onClick(url: String) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }

        }
        ossLicenseAdapter = OssLicenseAdapter(onOssLicenseClickListener)
        binding.ossLicenseRv.adapter = ossLicenseAdapter
        ossLicenseAdapter.submitList(license)
    }
}