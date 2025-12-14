package space.webkombinat.compass.components

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CompassSensor(
    update: (Float) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {

        val sensorManager =
            context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val accelerometerValues = FloatArray(3)
        val magneticValues = FloatArray(3)

        val listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent) {
                when (event.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER ->
                        System.arraycopy(event.values, 0, accelerometerValues, 0, 3)

                    Sensor.TYPE_MAGNETIC_FIELD ->
                        System.arraycopy(event.values, 0, magneticValues, 0, 3)
                }

                val rotationMatrix = FloatArray(9)
                val orientationAngles = FloatArray(3)

                if (SensorManager.getRotationMatrix(
                        rotationMatrix,
                        null,
                        accelerometerValues,
                        magneticValues
                    )
                ) {
                    SensorManager.getOrientation(rotationMatrix, orientationAngles)

                    val azimuth = Math.toDegrees(
                        orientationAngles[0].toDouble()
                    ).toFloat()

                    val degree = (azimuth + 360) % 360

                    update(degree)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        val accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        sensorManager.registerListener(
            listener,
            accel,
            SensorManager.SENSOR_DELAY_UI
        )
        sensorManager.registerListener(
            listener,
            magnet,
            SensorManager.SENSOR_DELAY_UI
        )

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }
}