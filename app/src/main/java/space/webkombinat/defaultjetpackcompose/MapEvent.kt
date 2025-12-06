package space.webkombinat.defaultjetpackcompose

import com.google.android.gms.maps.model.LatLng
import space.webkombinat.defaultjetpackcompose.data.ParkingSpot

sealed class MapEvent {
    object ToggleFalloutMap: MapEvent()
    data class OnMapLongClick(val latLng: LatLng): MapEvent()
    data class OnInfoWindowLongClick(val spot: ParkingSpot): MapEvent()
}
