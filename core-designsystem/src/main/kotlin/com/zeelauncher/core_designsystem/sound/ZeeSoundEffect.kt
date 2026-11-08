package com.zeelauncher.core_designsystem.sound

import androidx.annotation.RawRes
import com.zeelauncher.core_designsystem.R

enum class ZeeSoundEffect(@param:RawRes val resId: Int) {
    Move(R.raw.zee_move),
    Select(R.raw.zee_select),
    Back(R.raw.zee_back),
    Tab(R.raw.zee_tab),
}

fun Int.tapSound(selectedIndex: Int): ZeeSoundEffect =
    if (this == selectedIndex) ZeeSoundEffect.Select else ZeeSoundEffect.Move
