package com.zeelauncher.core_model.library

data class LibrarySyncResult(
    val addedCount: Int,
    val removedCount: Int,
) {
    val hasChanges: Boolean get() = addedCount > NO_CHANGE || removedCount > NO_CHANGE

    private companion object {
        const val NO_CHANGE = 0
    }
}
