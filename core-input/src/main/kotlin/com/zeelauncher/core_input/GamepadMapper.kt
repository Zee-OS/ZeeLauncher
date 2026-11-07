package com.zeelauncher.core_input

import android.view.KeyEvent

object GamepadMapper {

    fun toAction(keyCode: Int): ZeeAction? = when (keyCode) {
        KeyEvent.KEYCODE_DPAD_LEFT -> ZeeAction.Left
        KeyEvent.KEYCODE_DPAD_RIGHT -> ZeeAction.Right
        KeyEvent.KEYCODE_DPAD_UP -> ZeeAction.Up
        KeyEvent.KEYCODE_DPAD_DOWN -> ZeeAction.Down
        KeyEvent.KEYCODE_BUTTON_L1, KeyEvent.KEYCODE_Q -> ZeeAction.PreviousTab
        KeyEvent.KEYCODE_BUTTON_R1, KeyEvent.KEYCODE_E -> ZeeAction.NextTab
        KeyEvent.KEYCODE_BUTTON_A, KeyEvent.KEYCODE_ENTER, KeyEvent.KEYCODE_DPAD_CENTER -> ZeeAction.Confirm
        KeyEvent.KEYCODE_BUTTON_B, KeyEvent.KEYCODE_ESCAPE -> ZeeAction.Back
        KeyEvent.KEYCODE_BUTTON_Y, KeyEvent.KEYCODE_Y -> ZeeAction.Search
        else -> null
    }
}
