package xyz.tberghuis.surfcheckcompose.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.util.MimeTypes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.tberghuis.surfcheckcompose.data.camArray

@Composable
fun SurfcamScreen() {
  val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
  val scope = rememberCoroutineScope()
  val showSnackbarPlaybackError: (PlaybackException) -> Unit = { error ->
    scope.launch {
      scaffoldState.snackbarHostState.showSnackbar(error.toString())
    }
  }

  Scaffold(scaffoldState = scaffoldState,
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
      FloatingActionButton(onClick = {
        scope.launch {
          scaffoldState.drawerState.open()
        }
      }) {
        Text("X")
      }
    },

    drawerContent = { DrawerContent(scaffoldState, scope) },
    content = { contentPadding ->
      SurfcamContent(contentPadding, showSnackbarPlaybackError)
    }

  )
}

@Composable
fun DrawerContent(scaffoldState: ScaffoldState, scope: CoroutineScope) {
  val surfcamViewModel = viewModel<SurfcamViewModel>()
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
//    Text("the wreck")
    Spacer(modifier = Modifier.padding(20.dp))
    camArray.forEachIndexed { i, cam ->
      Text(
        cam.name, modifier = Modifier
          .padding(10.dp)
          .clickable {
            surfcamViewModel.currentCamIndexState.value = i
            scope.launch {
              scaffoldState.drawerState.close()
            }
          }, fontSize = 20.sp
      )
      Spacer(modifier = Modifier.padding(20.dp))
    }
  }
}

@Composable
fun SurfcamContent(
  contentPadding: PaddingValues, showSnackbarPlaybackError: (PlaybackException) -> Unit
) {
  var offset by remember { mutableStateOf(Offset.Zero) }
  var zoom by remember { mutableStateOf(1f) }
  Column(
    Modifier
      .padding(contentPadding)
      .background(Color(0xFFEDEAE0))
      .fillMaxSize()
      .pointerInput(Unit) {
        detectTransformGestures(onGesture = { _, pan, gestureZoom, _ ->
          val tmpZoom = zoom * gestureZoom
          zoom = if (tmpZoom < 1) 1f else tmpZoom
          offset += pan
        })
      }, verticalArrangement = Arrangement.Center
  ) {
    val modifier = Modifier.graphicsLayer {
      translationX = offset.x
      translationY = offset.y
      scaleX = zoom
      scaleY = zoom
    }
    VideoContainer(modifier, showSnackbarPlaybackError)
  }
}

@Composable
fun VideoContainer(modifier: Modifier, showSnackbarPlaybackError: (PlaybackException) -> Unit) {
  val surfcamViewModel = viewModel<SurfcamViewModel>()
  val url = camArray[surfcamViewModel.currentCamIndexState.value].url

  AndroidView(modifier = modifier, factory = {
    val playerInit = surfcamViewModel.initializePlayer(it).also { exoPlayer ->

      exoPlayer.addListener(object : Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
          super.onPlayerError(error)
          showSnackbarPlaybackError(error)
          Log.d("xxx", "PlaybackException $error")
        }
      })

      val mediaItem =
        MediaItem.Builder().setUri(url).setMimeType(MimeTypes.APPLICATION_M3U8).build()
      exoPlayer.setMediaItem(mediaItem)
      exoPlayer.playWhenReady = true
      exoPlayer.prepare()
    }


    StyledPlayerView(it).apply {
      useController = false
      player = playerInit
//      resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }
  }, update = {
    val mediaItem = MediaItem.Builder().setUri(url).setMimeType(MimeTypes.APPLICATION_M3U8).build()
    // reinitialize player if null???
    it.player?.setMediaItem(mediaItem)
    it.player?.prepare()
  })

  // I have no idea what i am doing
//  val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
//  DisposableEffect(lifecycleOwner) {
//    onDispose {
//      surfcamViewModel.player?.release()
//    }
//  }

}

