package com.arkadii.android002.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkadii.android002.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
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

    fun closeWebView(result: Boolean) {
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
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(URL_EXTRA_KEY, url)
            intent.putExtra(TOKEN_EXTRA_KEY, token)
            return intent
        }

        const val URL_EXTRA_KEY = "url_key"
        const val TOKEN_EXTRA_KEY = "token_key"
    }
}