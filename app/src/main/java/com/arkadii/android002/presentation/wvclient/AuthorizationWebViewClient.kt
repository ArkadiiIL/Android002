package com.arkadii.android002.presentation.wvclient

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arkadii.android002.presentation.interfaces.CloseWebView

class AuthorizationWebViewClient(private val activity: CloseWebView, private val token: String) :
    WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url?.toString()
        if (url != null) {
            return when (url) {
                getAuthUrl() -> false
                LOGIN_URL -> false
                SIGNUP_URL -> false
                getAllowUrl() -> {
                    false
                }

                else -> {
                    activity.closeWebView(false)
                    true
                }
            }
        } else {
            activity.closeWebView(false)
            return true
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if (url != null && url == getAllowUrl()) {
            activity.closeWebView(true)
        }
    }

    private fun getAuthUrl() = "$AUTH_URL$token"
    private fun getAllowUrl() = "${getAuthUrl()}$ALLOW"

    companion object {
        private const val AUTH_URL = "https://www.themoviedb.org/authenticate/"
        private const val LOGIN_URL = "https://www.themoviedb.org/login"
        private const val SIGNUP_URL = "https://www.themoviedb.org/signup"
        private const val ALLOW = "/allow"
    }

}