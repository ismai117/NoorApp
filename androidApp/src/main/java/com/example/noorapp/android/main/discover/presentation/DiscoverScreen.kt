package com.example.noor.android.main.discover.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noorapp.android.R
import com.example.noorapp.android.main.components.TopBar
import com.example.noorapp.android.navigation.screens.main.MainScreens
import com.example.noorapp.storage.Playlist
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    discoverViewModel: DiscoverViewModel = koinViewModel(),
    navController: NavController,
    playlist: (Playlist) -> Unit
) {

    val scaffoldState = rememberScaffoldState()

    val folders = discoverViewModel.allPlaylist.collectAsState().value
    val isLoading = discoverViewModel.isLoading.value

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { discoverViewModel.getAllPlaylist() }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                modifier = modifier,
                leadingIcon = null,
                title = "",
                trailingIcon = Icons.Filled.Settings,
                leadingIconClicked = {},
                trailingIconClicked = {
                    navController.navigate(MainScreens.SettingsScreen.route)
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF000000))
        ) {
            Image(
                painter = painterResource(id = R.drawable.texture_bg),
                contentDescription = "",
                modifier = modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFC0C0C0).copy(0.20f)),
                contentScale = ContentScale.Crop,
                alpha = 0.80f
            )
            LazyColumn(
                modifier = modifier.padding(top = 24.dp)
            ) {
                itemsIndexed(
                    items = folders
                ) { index, item ->
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(start = 12.dp, end = 12.dp)
                            .clickable {
                                playlist(item)
                            },
                        elevation = 4.dp,
                    ) {
                        Box(
                            modifier = modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "${item.name}",
                                color = Color.Black,
                                fontSize = 16.sp,
                                modifier = modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 12.dp, end = 12.dp),
                            )
                        }
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                modifier = modifier.align(Alignment.TopCenter),
                backgroundColor = Color(0xFFFFD300),
                contentColor = Color.Black
            )
        }
    }

}