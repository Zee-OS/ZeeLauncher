package com.dwiaziprasetya.zeelauncher

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.zeelauncher.core_common.dispatcher.IoDispatcher
import com.zeelauncher.core_database.DatabaseWarmer
import dagger.Lazy
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class ZeeLauncherApp : Application(), SingletonImageLoader.Factory {

    @Inject
    lateinit var imageLoader: Lazy<ImageLoader>

    @Inject
    lateinit var databaseWarmer: Lazy<DatabaseWarmer>

    @Inject
    @field:IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(SupervisorJob() + ioDispatcher).launch {
            databaseWarmer.get().warmUp()
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader = imageLoader.get()
}