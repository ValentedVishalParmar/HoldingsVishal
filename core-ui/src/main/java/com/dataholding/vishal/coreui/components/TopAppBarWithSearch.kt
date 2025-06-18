package com.dataholding.vishal.coreui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dataholding.vishal.coreui.R
import com.dataholding.vishal.coreui.util.TopBarMenu
import com.dataholding.vishal.coreui.widgets.CircleImageView
import com.dataholding.vishal.coreui.widgets.TextTitleMedium
//todo:: 18] create all required design component in this package
@Composable
fun TopAppBarWithSearch(
    title: String,
    modifier: Modifier = Modifier,
    onMenuClick: (menuId: Int)-> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 1.PROFILE IMAGE
        CircleImageView(
            imageId = R.drawable.ic_app_icon,
            size = 32.dp,
            modifier = Modifier.padding(16.dp)
        )

        // 2. TITLE NAME
        TextTitleMedium(textAlign = TextAlign.Start, text = title)

        Spacer(Modifier
            .weight(5f)
            .fillMaxHeight()) // height and background only for demonstration

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


    }


}

@Preview(name = "topAppBar", showBackground = true)
@Composable
fun TopAppBarWithSearchP() {
    TopAppBarWithSearch("Portfolio")
}