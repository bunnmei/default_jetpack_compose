package space.webkombinat.defaultjetpackcompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import space.webkombinat.defaultjetpackcompose.data.BottomNavigationItems

@Composable
fun BottomNavBar(
    expand: (Boolean) -> Unit,
    select: MutableState<Int>,
    items: List<BottomNavigationItems>,
    onItemClick: (BottomNavigationItems) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scroll = rememberScrollState()
    Row(
        modifier = modifier
            .height(60.dp)
            .width((100 * items.size).dp)
            .horizontalScroll(scroll)
            .background(color = Color.Gray)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed{ index, item ->
            Row (
                modifier = modifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {

                        onItemClick(item)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = modifier
                        .width(50.dp)
                        .height(30.dp)
                        .background(
                            color = if (select.value == index) {
                                Color.White.copy(0.3f)
                            } else {
                                Color.Transparent
                            },
                            shape = RoundedCornerShape(15.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                }
            }
//            NavigationBarItem(
//                modifier = modifier.width(120.dp),
//                selected = select.value == index,
//                onClick = {
//                    select.value = index
//                    onItemClick(item)
//                },
//                icon = {
//                    Icon(
//                        imageVector = item.icon,
//                        contentDescription = item.label
//                    )
//                }
//            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavBar() {
    BottomNavBar(
        expand = { _ -> },
        select = rememberSaveable { mutableStateOf(0) },
        items = listOf(
            BottomNavigationItems.Compass,
            BottomNavigationItems.Login,
            BottomNavigationItems.Mapbox,
            BottomNavigationItems.Level,
            BottomNavigationItems.Compass,
            BottomNavigationItems.Login,
            BottomNavigationItems.Mapbox,
            BottomNavigationItems.Level
        ),
        onItemClick = { _ -> }
    )
}
