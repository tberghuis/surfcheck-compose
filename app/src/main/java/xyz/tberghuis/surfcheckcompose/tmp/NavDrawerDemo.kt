package xyz.tberghuis.surfcheckcompose.tmp

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun NavDrawerDemo() {
  ScaffoldDemo()
}


@Composable
fun ScaffoldDemo() {

  val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

  val scope = rememberCoroutineScope()

  Scaffold(
    scaffoldState = scaffoldState,
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

    drawerContent = { Text(text = "drawerContent") },
    content = {

      ZoomVideoDemo()
    }

  )
}

@Composable
fun DrawerContent(scaffoldState: ScaffoldState, scope: CoroutineScope) {
  Column() {
    Text("the wreck")

  }
}
