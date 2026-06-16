package com.mhd.grit.app

import com.google.android.gms.ads.RequestConfiguration

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricPrompt
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.ads.MobileAds

import com.mhd.grit.R
import com.mhd.grit.core.data.Utils
import com.mhd.grit.core.presentation.component.InitialLoading
import com.mhd.grit.core.presentation.createNotificationChannel
import com.mhd.grit.core.presentation.settings.SettingsAction
import com.mhd.grit.core.presentation.theme.GritTheme
import com.mhd.grit.core.utils.LocalWindowSizeClass
import com.mhd.grit.viewmodels.MainViewModel
import com.mhd.grit.viewmodels.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class MainActivity : FragmentActivity() {
    private val settingsViewModel: SettingsViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.setAppUnlocked(true)
        MobileAds.initialize(this) {}
        installSplashScreen()
        createNotificationChannel(this)
        enableEdgeToEdge()

        setContent {
            val windowSizeClass = calculateWindowSizeClass(activity = this)

            // --- التعديل يبدأ هنا ---
            Column { // بداية العمود




                Box(modifier = androidx.compose.ui.Modifier.weight(1f)){

            CompositionLocalProvider(
                LocalWindowSizeClass provides windowSizeClass
            ) {
                val settingsState by settingsViewModel.state.collectAsStateWithLifecycle()
                val isAppUnlocked by mainViewModel.isAppUnlocked.collectAsStateWithLifecycle()
                var showContent by remember { mutableStateOf(false) }

                LaunchedEffect(settingsState.biometric, isAppUnlocked) {
                    settingsState.biometric?.let {
                        when {
                            !it -> {
                                showContent = true
                            }

                            isAppUnlocked -> {
                                showContent = true
                            }

                            else -> {
                                showBiometricPrompt(
                                    onSuccess = {
                                        mainViewModel.setAppUnlocked(true)
                                        showContent = true
                                    },
                                    onError = { errorCode, errString ->
                                        handleBiometricError(errorCode, errString) {
                                            showContent = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                GritTheme(
                    theme = settingsState.theme
                ) {
                    if (showContent) {
                        Grit()
                    } else {
                        InitialLoading()
                    }
                }
            }

        } // 1. كود الإعلان (صحيح الآن)
        androidx.compose.ui.viewinterop.AndroidView(
            modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
            factory = { context ->
                com.google.android.gms.ads.AdView(context).apply {
                    setAdSize(com.google.android.gms.ads.AdSize.BANNER)
                    adUnitId =  "TEST_AD_UNIT"
                    loadAd(com.google.android.gms.ads.AdRequest.Builder().build())
                }
            }
        ) }
        } }

    private fun showBiometricPrompt(
        onSuccess: () -> Unit,
        onError: (Int, CharSequence) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errorCode, errString)
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_lock))
            .setAllowedAuthenticators(Utils.getAuthenticators())
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun handleBiometricError(
        errorCode: Int,
        errString: CharSequence,
        onComplete: () -> Unit
    ) {
        when (errorCode) {
            BiometricPrompt.ERROR_USER_CANCELED,
            BiometricPrompt.ERROR_NEGATIVE_BUTTON,
            BiometricPrompt.ERROR_CANCELED -> {
                Toast.makeText(this, getString(R.string.biometric_failed), Toast.LENGTH_SHORT)
                    .show()
                finish()
            }

            BiometricPrompt.ERROR_NO_BIOMETRICS,
            BiometricPrompt.ERROR_HW_NOT_PRESENT,
            BiometricPrompt.ERROR_HW_UNAVAILABLE -> {
                mainViewModel.setAppUnlocked(true)
                settingsViewModel.onAction(SettingsAction.ChangeBiometricLock(false))
                Toast.makeText(
                    this,
                    getString(R.string.biometric_not_available),
                    Toast.LENGTH_LONG
                ).show()
                onComplete()
            }

            else -> {
                Toast.makeText(this, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}