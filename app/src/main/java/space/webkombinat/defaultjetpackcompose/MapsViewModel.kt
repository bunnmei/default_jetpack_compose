package space.webkombinat.defaultjetpackcompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.UrlTileProvider
import com.google.maps.android.compose.MapType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import space.webkombinat.defaultjetpackcompose.data.ParkingSpot
import space.webkombinat.defaultjetpackcompose.data.ParkingSpotRepository
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: ParkingSpotRepository
): ViewModel() {
    var state by mutableStateOf(MapState())

    private val _tileMap = MutableStateFlow(gsiTileProvider())
    val tileMap = _tileMap.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getParkingSpots().collectLatest { spots ->
                state = state.copy(
                    parkingSpots = spots
                )
            }
        }
    }

    private fun gsiTileProvider(): UrlTileProvider {
       return object : UrlTileProvider(256, 256) {
           override fun getTileUrl(x: Int, y: Int, zoom: Int): URL {
               return URL("https://cyberjapandata.gsi.go.jp/xyz/std/$zoom/$x/$y.png")
           }
       }
    }

    fun onEvent(event: MapEvent){
        when(event){
            is MapEvent.ToggleFalloutMap -> {
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if(state.isFalloutMap) {
                            null
                        }else {
                            MapStyleOptions(MapStyle.json)
                        }
                    )
                )
            }
            is MapEvent.OnMapLongClick -> {
                viewModelScope.launch {
                    repository.insertParkingSpot(
                        ParkingSpot(
                            event.latLng.latitude,
                            event.latLng.longitude
                        ))
                }
            }
            is MapEvent.OnInfoWindowLongClick -> {
                viewModelScope.launch {
                    repository.deleteParkingSpot(event.spot)
                }
            }
        }

    }
}