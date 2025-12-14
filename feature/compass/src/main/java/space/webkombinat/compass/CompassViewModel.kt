package space.webkombinat.compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class CompassViewModel: ViewModel() {

    private val _sensor = MutableStateFlow(0f)
    val sensor = _sensor.asStateFlow()

    fun update(num: Float) {
        _sensor.value = num
    }

}