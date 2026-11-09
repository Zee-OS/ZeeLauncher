package com.zeelauncher.core_common.home

import com.zeelauncher.core_common.attr.CommonAttribute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRequests @Inject constructor() {

    private val _requests = MutableSharedFlow<Unit>(extraBufferCapacity = CommonAttribute.Home.BUFFER_CAPACITY)
    val requests: SharedFlow<Unit> = _requests.asSharedFlow()

    fun request() {
        _requests.tryEmit(Unit)
    }
}
