package space.webkombinat.defaultjetpackcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AlertScreen(
    modifier: Modifier = Modifier
) {
    var visibleAlert by remember { mutableStateOf(false) }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if( visibleAlert ) {
            AlertDialog(
                title = { Text(text = "通知") },
                text = { Text(text = "ドローンのアームに成功しました。") },
                onDismissRequest = {
                    visibleAlert = false
                    // ダイアログの外側をタップした時や戻るボタンを押した時の処理
                    // 何もしない、または onConfirmation() を呼ぶなど
                },
                confirmButton = {
                    TextButton(onClick = { visibleAlert = false }) {
                        Text("OK")
                    }
                },
                // dismissButton は書かない（または null を渡す）
                dismissButton = null
            )
        }

        Button(
            onClick = {
                visibleAlert = true
            }
        ) {
            Text("")
        }
    }


}