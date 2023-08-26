package com.kaizenplus.tamatemassignment.presentation.screens.webview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kaizenplus.tamatemassignment.R
import com.kaizenplus.tamatemassignment.databinding.ActivityWebViewBinding
import com.kaizenplus.tamatemassignment.presentation.di.AppModule
import com.kaizenplus.tamatemassignment.presentation.screens.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private val viewModel: WebViewModel by viewModels()

    companion object {
        // Static method that returns an Intent
        private const val URL_INTENT = "URL"
        private lateinit var intent: Intent
        fun getIntent(context: Context, url: String): Intent {
            intent = Intent(context, WebViewActivity::class.java).putExtra(URL_INTENT, url)
            return intent
        }
    }

    lateinit var url: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        url = intent.getStringExtra(URL_INTENT) ?: "https://google.com"
        viewModel.actionTrigger(WebViewModel.UIAction.LoadUrl)
        collectUIState()
    }

    private fun collectUIState() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                it?.let { it1 -> handleUiState(it1) }
            }
        }
    }

    private fun handleUiState(uiState: WebViewModel.UiState) {
        binding.progressbar.isVisible = uiState.isLoading
        when (uiState.action) {
            is WebViewModel.UIAction.LoadUrl -> {
                binding.webView.webViewClient = WebViewClient()
                binding.webView.loadUrl(url)
                binding.webView.settings.javaScriptEnabled = true
                binding.webView.settings.domStorageEnabled = true
                binding.webView.settings.loadWithOverviewMode = true
                binding.webView.settings.useWideViewPort = true
                binding.webView.webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        binding.forwardButton.setColorFilter(
                            ContextCompat.getColor(
                                this@WebViewActivity,
                                if (!binding.webView.canGoForward()) R.color.grey else R.color.blackWhite
                            )
                        )
                        binding.backButton.setColorFilter(
                            ContextCompat.getColor(
                                this@WebViewActivity,
                                if (!binding.webView.canGoBack()) R.color.grey else R.color.blackWhite
                            )
                        )
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        viewModel.actionTrigger(WebViewModel.UIAction.Steady)
                    }
                }
                binding.backButton.setOnClickListener {
                    viewModel.actionTrigger(WebViewModel.UIAction.MoveBack)

                }
                binding.forwardButton.setOnClickListener {
                    viewModel.actionTrigger(WebViewModel.UIAction.MoveForward)

                }
                binding.refreshButton.setOnClickListener {
                    viewModel.actionTrigger(WebViewModel.UIAction.Refresh)
                }
                binding.closeButton.setOnClickListener {
                    viewModel.actionTrigger(WebViewModel.UIAction.Finish)
                }
            }

            is WebViewModel.UIAction.MoveForward -> if (binding.webView.canGoForward()) {
                binding.webView.goForward()
            } else viewModel.actionTrigger(WebViewModel.UIAction.Steady)

            is WebViewModel.UIAction.MoveBack -> if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else viewModel.actionTrigger(WebViewModel.UIAction.Steady)

            is WebViewModel.UIAction.Refresh -> binding.webView.reload()
            is WebViewModel.UIAction.Finish -> finish()
            else -> {
            }
        }

    }
}