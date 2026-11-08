package com.zeelauncher.core_common.home

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

private const val HOME_REQUEST_BUFFER_CAPACITY = 1

@Singleton
class HomeRequests @Inject constructor() {

    private val _requests = MutableSharedFlow<Unit>(extraBufferCapacity = HOME_REQUEST_BUFFER_CAPACITY)
    val requests: SharedFlow<Unit> = _requests.asSharedFlow()

    fun request() {
        _requests.tryEmit(Unit)
    }
}
