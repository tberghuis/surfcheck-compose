package xyz.tberghuis.surfcheckcompose.tmp

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.MimeTypes
import xyz.tberghuis.surfcheckcompose.R
import kotlin.math.roundToInt

@Composable
fun ZoomVideoDemo() {
  var offset by remember { mutableStateOf(Offset.Zero) }
  var zoom by remember { mutableStateOf(1f) }


  Column(
    Modifier
      .background(Color(0xFFEDEAE0))
      .fillMaxSize()
      .pointerInput(Unit) {
        detectTransformGestures(
          onGesture = { _, pan, gestureZoom, _ ->
            zoom *= gestureZoom
            offset += pan
          }
        )
      },
    verticalArrangement = Arrangement.Center
  ) {


    val modifier = Modifier

      .graphicsLayer {
        translationX = offset.x
        translationY = offset.y
        scaleX = zoom
        scaleY = zoom
      }

    VideoContainer(modifier)
  }


}

@Composable
fun VideoContainer(modifier: Modifier) {
  val playerViewModel = viewModel<PlayerViewModel>()

  AndroidView(modifier = modifier, factory = {
    val playerInit = playerViewModel.initializePlayer(it).also { exoPlayer ->
      val mediaItem = MediaItem.Builder()
        .setUri("https://cams.cdn-surfline.com/cdn-au/au-ballinalighthouse/chunklist.m3u8")
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


class PlayerViewModel : ViewModel() {
  // lateinit??? nah cause context may change???
  var player: SimpleExoPlayer? = null

  fun initializePlayer(context: Context): SimpleExoPlayer {
    player = SimpleExoPlayer.Builder(context).build()
    return player!!
  }
}
