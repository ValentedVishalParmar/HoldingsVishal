package com.example.holdingsvishal.ui.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dataholding.vishal.coreui.R
import com.dataholding.vishal.coreui.util.TopBarMenu
import com.dataholding.vishal.coreui.widgets.CircleImageView

//todo:: 18] create all required design component in this package
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithSearch(
    title: String,
    modifier: Modifier = Modifier,
    onMenuClick: (menuId: Int)-> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            // 1. TITLE NAME
            Text(text = title, color = Color.Black)
        },
        navigationIcon = {
            // 2.PROFILE IMAGE
            CircleImageView(
                imageId = R.drawable.ic_app_icon,
                size = 32.dp,
                modifier = Modifier.padding(16.dp)
            )
        },
        actions = {
            // 3. UPDOWN ICON
            IconButton({onMenuClick(TopBarMenu.VALUES.id)}) {
                Icon(imageVector = Icons.Default.BarChart, contentDescription = null)
            }

            VerticalDivider(
                modifier = Modifier.padding(0.dp,12.dp)
                    .fillMaxHeight()  //fill the max height
                    .width(2.dp)
            )

            // 4. SEARCH ICON
            IconButton({onMenuClick(TopBarMenu.SEARCH.id)}) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        },
    )

}

@Preview(name = "topAppBar", showBackground = true)
@Composable
fun TopAppBarWithSearchP() {
    TopAppBarWithSearch("Portfolio")
}