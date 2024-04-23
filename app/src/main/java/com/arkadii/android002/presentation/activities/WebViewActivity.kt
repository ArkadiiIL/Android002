package com.arkadii.android002.presentation.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkadii.android002.databinding.ActivityWebViewBinding
import com.arkadii.android002.presentation.interfaces.CloseWebView
import com.arkadii.android002.presentation.wvclient.AuthorizationWebViewClient

class WebViewActivity : AppCompatActivity(), CloseWebView {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = intent.getStringExtra(URL_EXTRA_KEY)
        val token = intent.getStringExtra(TOKEN_EXTRA_KEY)
        if (url != null && token != null) {
            binding.webView.webViewClient = AuthorizationWebViewClient(this, token)
            binding.webView.loadUrl(url)
            binding.webView.settings.javaScriptEnabled = true
        } else {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    override fun closeWebView(result: Boolean) {
        if (result) {
            setResult(Activity.RESULT_OK)
        } else setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    companion object {
        fun getIntent(context: Context, url: String, token: String): Intent {
            return Intent(context, WebViewActivity::class.java).apply {
                putExtra(URL_EXTRA_KEY, url)
                putExtra(TOKEN_EXTRA_KEY, token)
            }
        }

        const val URL_EXTRA_KEY = "url_key"
        const val TOKEN_EXTRA_KEY = "token_key"
    }
}