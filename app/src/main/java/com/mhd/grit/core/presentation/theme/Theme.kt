package com.mhd.grit.core.presentation.theme

import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.mhd.grit.core.domain.AppTheme
import com.mhd.grit.core.domain.Fonts

data class Theme(
    val appTheme: AppTheme = AppTheme.SYSTEM,
    val isAmoled: Boolean = true,
    val isMaterialYou: Boolean = false,
    val font: Fonts = Fonts.MONTSERRAT,
    val paletteStyle: PaletteStyle = PaletteStyle.Vibrant,
    val seedColor: Color = Color(0xFF2196F3)
)