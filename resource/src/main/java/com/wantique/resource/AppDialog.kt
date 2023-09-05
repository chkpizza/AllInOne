package com.wantique.resource

import android.R
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.wantique.resource.databinding.LayoutAppDialogBinding

class AppDialog(
    private val body: String,
    private val onPositiveButtonClickListener: (Dialog) -> Unit,
    private val onNegativeButtonClickListener: (Dialog) -> Unit,
    private val positiveButtonText: String,
    private val negativeButtonText: String,
    context: Context
) : Dialog(context) {
    private lateinit var binding: LayoutAppDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutAppDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        setCancelable(false)

        window?.setBackgroundDrawable(ColorDrawable(context.resources.getColor(R.color.transparent, null)))

        defaultDialogTvPositive.setOnClickListener {
            onPositiveButtonClickListener(this@AppDialog)
        }

        defaultDialogTvNegative.setOnClickListener {
            onNegativeButtonClickListener(this@AppDialog)
        }

        defaultDialogTvBody.text = body
        defaultDialogTvPositive.text = positiveButtonText
        defaultDialogTvNegative.text = negativeButtonText

        resize(0.8f)
    }

    private fun resize(width: Float): AppDialog {
        WindowManager.LayoutParams().also {
            it.copyFrom(window?.attributes)
            it.width = (context.resources.displayMetrics.widthPixels * width).toInt()
            window?.attributes = it
        }

        return this
    }

    class Builder(private val context: Context) {
        private lateinit var body: String
        private lateinit var onPositiveButtonClickListener: (Dialog) -> Unit
        private lateinit var onNegativeButtonClickListener: (Dialog) -> Unit
        private lateinit var positiveButtonText: String
        private lateinit var negativeButtonText: String

        fun setBody(body: String): Builder = apply {
            this.body = body
        }

        fun setPositionButtonClickListener(onClickListener: (Dialog) -> Unit) = apply {
            onPositiveButtonClickListener = onClickListener
        }

        fun setNegativeButtonClickListener(onClickListener: (Dialog) -> Unit) = apply {
            onNegativeButtonClickListener = onClickListener
        }

        fun setPositiveButtonText(text: String) = apply {
            positiveButtonText = text
        }

        fun setNegativeButtonText(text: String) = apply {
            negativeButtonText = text
        }

        fun show(): AppDialog = AppDialog(body, onPositiveButtonClickListener, onNegativeButtonClickListener, positiveButtonText, negativeButtonText, context).apply {
            show()
        }
    }
}