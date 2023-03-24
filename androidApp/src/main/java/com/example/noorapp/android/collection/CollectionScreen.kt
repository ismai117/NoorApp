package com.example.noorapp.android.collection

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noor.android.collection.CollectionViewModel
import com.example.noorapp.storage.Playlist
import com.example.noorapp.android.R
import com.example.noorapp.android.main.components.TopBar
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    collectionViewModel: CollectionViewModel = koinViewModel(),
    navController: NavController,
    playlist: Playlist?,
    backPressed: () -> Unit,
    playVideo: (String, String) -> Unit
) {

    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    val videos = collectionViewModel.playlist.collectAsState().value
    val isLoading = collectionViewModel.isLoading.value

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = {
            collectionViewModel.getPlaylist(
                path = playlist?.path ?: ""
            )
        }
    )

    LaunchedEffect(Unit) {
        collectionViewModel.getPlaylist(
            path = playlist?.path ?: ""
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                modifier = modifier,
                leadingIcon = Icons.Filled.ArrowBack,
                title = "",
                trailingIcon = null,
                leadingIconClicked = {
                    backPressed()
                }) {
            }
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    if (!isLoading) {
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp)
                        ) {
                            playlist?.name?.let {
                                Text(
                                    text = it,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = modifier.padding(start = 12.dp, end = 12.dp)
                                )
                            }
                        }
                        Spacer(modifier = modifier.padding(24.dp))
                    }
                }

                items(
                    items = videos
                ) { item ->
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .padding(top = 16.dp)
                            .clickable {
                                playVideo(item.name.removeSuffix(".mp3"), item.path)
                            },
                        backgroundColor = Color(0xFF353535),
                        elevation = 4.dp,
                    ) {
                        Box(
                            modifier = modifier.fillMaxSize()
                        ) {
                            Text(
                                text = item.name.removeSuffix(".mp3"),
                                color = Color.White,
                                modifier = modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 12.dp)
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


