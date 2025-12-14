package space.webkombinat.compass

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import space.webkombinat.compass.components.CompassSensor
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun CompassScreen(
    vm: CompassViewModel,
    modifier: Modifier = Modifier
){
    val uiState = vm.sensor.collectAsState()
    CompassSensor(update = {
        vm.update(it)
    })
    CompassScreen(
        angleDeg = uiState.value
    )

}

@Composable
private fun CompassScreen(
    modifier: Modifier = Modifier,
    angleDeg: Float = 0f
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Canvas(
            modifier = modifier
                .size(200.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
        ) {
            val center = center
            val radius = size.minDimension / 2 * 0.8f

            // 角度をラジアンに変換（-90°で北を上に）
            val rad = Math.toRadians((angleDeg - 90).toDouble())

            val end = Offset(
                x = center.x + radius * cos(rad).toFloat(),
                y = center.y + radius * sin(rad).toFloat()
            )

            // 針
            drawLine(
                color = Color.Red,
                start = center,
                end = end,
                strokeWidth = 6f,
                cap = StrokeCap.Round
            )

            // 中心の丸
            drawCircle(
                color = Color.Black,
                radius = 8f,
                center = center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewCompassScreen() {
    CompassScreen(angleDeg = 0f)
}