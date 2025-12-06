package space.webkombinat.defaultjetpackcompose

import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import space.webkombinat.defaultjetpackcompose.data.ParkingSpot

data class MapState(
    val properties: MapProperties = MapProperties(
//        mapType = MapType.SATELLITE
        mapType = MapType.NONE
//        mapStyleOptions = MapStyleOptions(MapStyle.json)
    ),
    val parkingSpots: List<ParkingSpot> = emptyList(),
    val isFalloutMap: Boolean = false,
)
