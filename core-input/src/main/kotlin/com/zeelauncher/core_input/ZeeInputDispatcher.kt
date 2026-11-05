package com.zeelauncher.core_input

import android.view.KeyEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

private const val ACTION_BUFFER_CAPACITY = 16

class ZeeInputDispatcher {

    private val _actions = MutableSharedFlow<ZeeAction>(extraBufferCapacity = ACTION_BUFFER_CAPACITY)
    val actions: SharedFlow<ZeeAction> = _actions.asSharedFlow()

    fun dispatch(event: KeyEvent): Boolean {
        val action = GamepadMapper.toAction(event.keyCode) ?: return false
        if (event.action == KeyEvent.ACTION_DOWN) _actions.tryEmit(action)
        return true
    }
}
