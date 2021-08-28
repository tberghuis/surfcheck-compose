package xyz.tberghuis.surfcheckcompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.MimeTypes
import xyz.tberghuis.surfcheckcompose.databinding.ActivitySandboxBinding

// so lets get exoplayer hello world happening
// https://cams.cdn-surfline.com/cdn-au/au-ballinalighthouse/chunklist.m3u8

class SandboxActivity : AppCompatActivity() {
  private var player: SimpleExoPlayer? = null
  private var playWhenReady = true
  private var currentWindow = 0
  private var playbackPosition = 0L
  private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
    ActivitySandboxBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(viewBinding.root)

//     val viewModel by viewModels<MyViewModel>()
  }

  public override fun onStart() {
    super.onStart()
    initializePlayer()
  }

  private fun initializePlayer() {
    player = SimpleExoPlayer.Builder(this)
      .build()
      .also { exoPlayer ->
        viewBinding.videoView.player = exoPlayer
        val mediaItem = MediaItem.Builder()
          .setUri("https://cams.cdn-surfline.com/cdn-au/au-ballinalighthouse/chunklist.m3u8")
          .setMimeType(MimeTypes.APPLICATION_M3U8)
          .build()
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.playWhenReady = playWhenReady
        exoPlayer.seekTo(currentWindow, playbackPosition)
        exoPlayer.prepare()
      }
  }
}