package com.zeelauncher.core_database.attr

internal object DatabaseAttribute {

    object Database {
        const val NAME = "zee_launcher.db"
        const val VERSION = 1
    }

    object LibraryGame {
        const val TABLE = "library_game"
        const val COLUMN_PACKAGE_NAME = "package_name"
        const val COLUMN_ACTIVITY_NAME = "activity_name"
        const val COLUMN_LABEL = "label"
        const val COLUMN_LAST_PLAYED_AT = "last_played_at"
    }

    object HiddenGame {
        const val TABLE = "hidden_game"
        const val COLUMN_PACKAGE_NAME = "package_name"
    }
}
