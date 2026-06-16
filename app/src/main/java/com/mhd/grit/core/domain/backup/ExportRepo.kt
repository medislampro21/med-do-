package com.mhd.grit.core.domain.backup

interface ExportRepo {
    suspend fun exportToJson()
}

enum class ExportState {
    IDLE,
    EXPORTING,
    EXPORTED
}