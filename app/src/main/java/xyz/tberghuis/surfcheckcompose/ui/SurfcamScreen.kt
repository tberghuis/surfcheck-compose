package xyz.tberghuis.surfcheckcompose.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.MimeTypes
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SurfcamScreen() {
  val urls = arrayOf(
    "https://cams.cdn-surfline.com/cdn-au/au-byronbay/playlist.m3u8",
    "https://cams.cdn-surfline.com/cdn-au/au-thepassoverview/playlist.m3u8",
    "https://cams.cdn-surfline.com/cdn-au/au-lennoxhead/playlist.m3u8",
    "https://cams.cdn-surfline.com/cdn-au/au-ballinashellybeach/playlist.m3u8",
    "https://cams.cdn-surfline.com/cdn-au/au-ballinalighthouse/chunklist.m3u8",
    "https://cams.cdn-surfline.com/cdn-au/au-evanshead/playlist.m3u8"
  )
  val pagerState = rememberPagerState(pageCount = urls.size)
  VerticalPager(state = pagerState) { page ->
    val pageDelta = page - currentPage
    Log.d("xxx", "page $page currentPage $currentPage")
    Box(modifier = Modifier.fillMaxSize()) {
      if (pageDelta.absoluteValue > 1) {
        // should be offscreen
        Text(urls[page])
      } else {
        SurfcamPlayer(urls[page], page)
      }
    }
//    LaunchedEffect(pagerState) {
//      snapshotFlow { pagerState.currentPage }.collect { page ->
//        // Selected page has changed...
//        //todo
//      }
//    }
  }
}


@Composable
fun SurfcamPlayer(url: String, page: Int) {

  val surfcamViewModel = viewModel<SurfcamViewModel>()

  AndroidView(factory = {
    val playerInit = surfcamViewModel.initializePlayer(it, page).also { exoPlayer ->
      val mediaItem = MediaItem.Builder()
        .setUri(url)
        .setMimeType(MimeTypes.APPLICATION_M3U8)
        .build()
      exoPlayer.setMediaItem(mediaItem)
      exoPlayer.playWhenReady = true
      exoPlayer.prepare()
    }
    PlayerView(it).apply {
      useController = false
      player = playerInit
//      resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }
  })
}