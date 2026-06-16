package com.mhd.grit.core.presentation.settings

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.mhd.grit.core.domain.AppTheme
import com.mhd.grit.core.domain.Fonts
import com.mhd.grit.core.domain.Pages
import com.mhd.grit.core.domain.backup.ExportState
import com.mhd.grit.core.domain.backup.RestoreState
import com.mhd.grit.core.presentation.theme.Theme
import kotlinx.datetime.DayOfWeek
import kotlin.Boolean

@Stable
@Immutable
data class SettingsState(
    val theme: Theme = Theme(),
    val is24Hr: Boolean = false,
    val reorderTasks: Boolean = false,
    val startOfTheWeek: DayOfWeek = DayOfWeek.MONDAY,
    val pauseNotifications: Boolean = false,
    val startingPage: Pages = Pages.Tasks,
    val backupState: BackupState = BackupState(),
    val biometric: Boolean? = null,
    val biometricAvailable: Boolean = false,
    val showPaywall: Boolean = false,
    val isUserSubscribed: Boolean = false,
    val serverState: ServerState = ServerState()
)

@Stable
@Immutable
data class ServerState(
    val isRunning: Boolean = false,
    val serverPort: Int = 8080,
    val serverUrl: String? = null
)

@Stable
@Immutable
data class BackupState(
    val exportState: ExportState = ExportState.IDLE,
    val restoreState: RestoreState = RestoreState.IDLE
)