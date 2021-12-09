package xyz.tberghuis.surfcheckcompose.tmp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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

    drawerContent = { DrawerContent(scaffoldState, scope) },
    content = {

      ZoomVideoDemo()
    }

  )
}

@Composable
fun DrawerContent(scaffoldState: ScaffoldState, scope: CoroutineScope) {

  val playerViewModel = viewModel<PlayerViewModel>()

  Column(
    modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
  ) {
//    Text("the wreck")
    Spacer(modifier = Modifier.padding(20.dp))
    camArray.forEachIndexed { i, cam ->
      Text(cam.name, modifier = Modifier.clickable {
        playerViewModel.currentCamIndexState.value = i
        scope.launch {
          scaffoldState.drawerState.close()
        }
      })
      Spacer(modifier = Modifier.padding(20.dp))
    }

  }
}
