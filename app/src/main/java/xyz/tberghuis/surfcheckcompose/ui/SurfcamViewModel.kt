package xyz.tberghuis.surfcheckcompose.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.SimpleExoPlayer

class SurfcamViewModel : ViewModel() {
  // the theory is that pager can reuse 3 instances of player
  // consecutive pages to not share the same instance of player
  var players: Array<SimpleExoPlayer?> = arrayOfNulls(3)
  fun initializePlayer(context: Context, page: Int): SimpleExoPlayer {
    val mod = page % 3
    players[mod] = players[mod] ?: SimpleExoPlayer.Builder(context).build()
    return players[mod]!!
  }
}