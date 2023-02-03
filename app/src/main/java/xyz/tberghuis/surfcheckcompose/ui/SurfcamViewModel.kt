package xyz.tberghuis.surfcheckcompose.ui

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.SimpleExoPlayer


class SurfcamViewModel : ViewModel() {
  // lateinit??? nah cause context may change???
  var player: SimpleExoPlayer? = null

  // default to northwall
  val currentCamIndexState = mutableStateOf(3)


  fun initializePlayer(context: Context): SimpleExoPlayer {
    player = SimpleExoPlayer.Builder(context).build()
    return player!!
  }
}
