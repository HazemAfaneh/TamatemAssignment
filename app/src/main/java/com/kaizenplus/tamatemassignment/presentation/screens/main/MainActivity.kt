package com.kaizenplus.tamatemassignment.presentation.screens.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kaizenplus.tamatemassignment.R
import com.kaizenplus.tamatemassignment.presentation.di.AppModule
import com.kaizenplus.tamatemassignment.presentation.screens.webview.WebViewActivity
import com.kaizenplus.tamatemassignment.presentation.screens.theme.TamatemAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var splashScreen: SplashScreen
    private val viewModel: MainViewModel by viewModels()


    @AppModule.TamatemURL
    @Inject
    lateinit var tamatemUrl: String

    @AppModule.GoogleURL
    @Inject
    lateinit var googleURL: String

    @AppModule.FacebookURL
    @Inject
    lateinit var facebookURL: String

    @AppModule.InstagramURL
    @Inject
    lateinit var instagramURL: String
    lateinit var url: String

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashScreen = installSplashScreen()
        setContent {
            TamatemAssignmentTheme {
                var modalState by remember { mutableStateOf(ModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)) }

                // A surface container using the 'background' color from the theme
                OpenLinkButton(
                    backgroundColor = Color(0xFF1877F2),
                    text = "Open Tamatem"
                ) {
                    lifecycleScope.launch {
                        modalState.show()
                    }
                }
                ModalBottomSheetWithHorizontalActions(modalState) {
                    this.url = it
                    viewModel.actionTrigger(MainViewModel.UIAction.CheckInternetConnection)
                }
                collectUIState()
            }
        }
    }

    private fun collectUIState() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                handleUiState(it)
            }
        }

    }

    private fun handleUiState(uiState: MainViewModel.UiState) {
        uiState.isInternetExist?.let {
            if (it) {
                startActivity(WebViewActivity.getIntent(this, url))
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.actionTrigger(MainViewModel.UIAction.Steady)


    }
    private var urlEditTextState by mutableStateOf(TextFieldValue())
    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun ModalBottomSheetWithHorizontalActions(
        state: ModalBottomSheetState,
        clickEvent: (url: String) -> Unit
    ) {
        val scope = rememberCoroutineScope()
        urlEditTextState = TextFieldValue()
        if (state.isVisible) {
            ModalBottomSheet(
                windowInsets = WindowInsets(0.dp),
                onDismissRequest = {
                    scope.launch {
                        state.hide()
                    }
                })
            {
                Column(
                    Modifier.navigationBarsPadding(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val items = listOf(
                        R.drawable.tamatem_logo to "Tamatem" to tamatemUrl,
                        R.drawable.ic_google to "Google" to googleURL,
                        R.drawable.ic_facebook to "Facebook" to facebookURL,
                        R.drawable.ic_instagram to "Instagram" to instagramURL,
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(items) { item ->
                            Column(
                                modifier = Modifier
                                    .width(96.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        clickEvent(item.second)
                                    }
                                    .padding(vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Image(
                                    painter = painterResource(id = item.first.first),
                                    contentDescription = "",
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = item.first.second,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
                OutlinedTextField(
                    value = urlEditTextState,
                    onValueChange = { urlEditTextState = it },
                    label = { Text("Enter URL") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                // Button to add the URL
                Button(
                    onClick = {
                        clickEvent(urlEditTextState.text)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                ) {
                    Text("Add URL")
                }
            }
        }
    }

}

@Composable
fun OpenLinkButton(
    backgroundColor: Color,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.wrapContentWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tamatem_logo),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
                Text(text, color = Color.White)
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TamatemAssignmentTheme {

    }
}