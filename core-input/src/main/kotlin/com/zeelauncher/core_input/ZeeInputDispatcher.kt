package com.zeelauncher.core_input

import android.view.KeyEvent

fun interface ZeeActionHandler {
    fun onAction(action: ZeeAction): Boolean
}

class ZeeInputDispatcher {

    private val handlers = mutableListOf<ZeeActionHandler>()
    private val consumedKeys = mutableSetOf<Int>()

    fun register(handler: ZeeActionHandler) {
        handlers.add(handler)
    }

    fun unregister(handler: ZeeActionHandler) {
        handlers.remove(handler)
    }

    fun dispatch(action: ZeeAction): Boolean =
        handlers.toList().asReversed().any { it.onAction(action) }

    fun dispatch(event: KeyEvent): Boolean {
        val action = GamepadMapper.toAction(event.keyCode) ?: return false
        return when (event.action) {
            KeyEvent.ACTION_DOWN -> {
                val handled = dispatch(action)
                if (handled) consumedKeys.add(event.keyCode)
                handled
            }
            KeyEvent.ACTION_UP -> consumedKeys.remove(event.keyCode)
            else -> false
        }
    }
}
