package space.webkombinat.defaultjetpackcompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.android.gms.maps.model.UrlTileProvider
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.TileOverlay
import com.google.maps.android.compose.rememberCameraPositionState
import java.net.URL
import kotlin.system.measureTimeMillis

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapsViewModel = viewModel()
) {
    val scaffoldState = rememberScrollState()
    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }
    Scaffold (
//        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(MapEvent.ToggleFalloutMap)
            }){
                Icon(
                    imageVector = if(viewModel.state.isFalloutMap) { Icons.Default.ToggleOff}
                        else {Icons.Default.ToggleOn},
                    contentDescription = "Toggle Fallout map"
                )
            }
        }
    ) { paddingValues ->

        val cameraPositionState = rememberCameraPositionState()
        val currentProvider = remember { gsiTileProvider() }

        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = modifier.fillMaxSize().padding(paddingValues),
            properties = viewModel.state.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                viewModel.onEvent(MapEvent.OnMapLongClick(it))
            }
        ) {
            TileOverlay(
                tileProvider = currentProvider,
                zIndex = 0f,
                fadeIn = true
            )
        }


    }
    
}



fun gsiTileProvider(): UrlTileProvider {
    return object : UrlTileProvider(256, 256) {

        override fun getTileUrl(x: Int, y: Int, zoom: Int): URL {
            return URL("https://cyberjapandata.gsi.go.jp/xyz/std/$zoom/$x/$y.png")
        }

    }
}