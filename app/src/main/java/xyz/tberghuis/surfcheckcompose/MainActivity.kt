package xyz.tberghuis.surfcheckcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import xyz.tberghuis.surfcheckcompose.tmp.NavDrawerDemo
import xyz.tberghuis.surfcheckcompose.tmp.ZoomVideoDemo
import xyz.tberghuis.surfcheckcompose.ui.SurfcamScreen
import xyz.tberghuis.surfcheckcompose.ui.theme.SurfcheckComposeTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SurfcheckComposeTheme {
//        SurfcamScreen()
//        ZoomVideoDemo()

        NavDrawerDemo()
      }
    }
  }
}