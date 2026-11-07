package com.zeelauncher.core_scanner.icon

import coil3.ImageLoader
import coil3.asImage
import coil3.decode.DataSource
import coil3.fetch.FetchResult
import coil3.fetch.Fetcher
import coil3.fetch.ImageFetchResult
import coil3.key.Keyer
import coil3.request.Options
import com.zeelauncher.core_common.constant.SLASH
import com.zeelauncher.core_common.dispatcher.IoDispatcher
import com.zeelauncher.core_model.app.AppIcon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AppIconFetcher(
    private val icon: AppIcon,
    private val renderer: AppIconRenderer,
    private val ioDispatcher: CoroutineDispatcher,
) : Fetcher {

    override suspend fun fetch(): FetchResult? = withContext(ioDispatcher) {
        renderer.render(icon)?.let { bitmap ->
            ImageFetchResult(
                image = bitmap.asImage(),
                isSampled = false,
                dataSource = DataSource.DISK,
            )
        }
    }

    class Factory @Inject constructor(
        private val renderer: AppIconRenderer,
        @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : Fetcher.Factory<AppIcon> {

        override fun create(data: AppIcon, options: Options, imageLoader: ImageLoader): Fetcher =
            AppIconFetcher(data, renderer, ioDispatcher)
    }
}

internal class AppIconKeyer @Inject constructor() : Keyer<AppIcon> {

    override fun key(data: AppIcon, options: Options): String =
        data.packageName + SLASH + data.activityName
}
