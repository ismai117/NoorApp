@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)

package com.example.noorapp.android.player


import android.util.Log
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.PlayerView
import com.example.noorapp.android.R
import com.example.noorapp.android.notes.NotesViewModel
import com.example.noorapp.notes.domain.Notes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    playerViewModel: PlayerViewModel = koinViewModel(),
    notesViewModel: NotesViewModel = koinViewModel(),
    lesson: String?,
    video: String?
) {

    val context = LocalContext.current
    val scaffoldState1 = rememberScaffoldState()
    val scaffoldState2 = rememberScaffoldState()
    val addNoteScrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val hideKeyboard = LocalSoftwareKeyboardController.current

    val bottomSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit){
        notesViewModel.searchQuery("")
    }

    val isSearchVisible = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }

    LaunchedEffect(isSearchVisible.value) {
        if (isSearchVisible.value) {
            focusRequester.requestFocus()
        }
    }

    val uri = playerViewModel.video.collectAsState().value
    val isLoading = playerViewModel.isLoading.value

    val isPlayerViewShown = remember {
        mutableStateOf(false)
    }

    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(uri) {
        if (uri?.isNotBlank() == true) {
            playerViewModel.playVideo(uri)
        }
    }

    LaunchedEffect(Unit) {
        video?.let { playerViewModel.getVideo(it) }
    }

    val notes = notesViewModel.notes.collectAsState().value
    val savedNote = remember { mutableStateOf<Notes?>(Notes()) }

    val dialogAddNoteOpened = rememberSaveable { mutableStateOf(false) }
    val addNoteText = remember { mutableStateOf("") }

    val dialogUpdateNoteOpened = rememberSaveable { mutableStateOf(false) }
    var updateNoteText by remember {
        mutableStateOf(value = TextFieldValue(text = savedNote.value!!.note))
    }

    LaunchedEffect(dialogAddNoteOpened.value) {
        if (dialogAddNoteOpened.value) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(dialogUpdateNoteOpened.value) {
        if (dialogUpdateNoteOpened.value) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(savedNote.value?.note) {
        if (savedNote.value?.note?.isNotBlank() == true) {
            updateNoteText =
                TextFieldValue(savedNote.value?.note!!, TextRange(savedNote.value!!.note.length))
        }
    }

    val showDeleteBtn = rememberSaveable { mutableStateOf(false) }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Scaffold(
                scaffoldState = scaffoldState2,
                topBar = {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color(0xFF000000))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.texture_bg),
                            contentDescription = "",
                            modifier = modifier
                                .fillMaxSize()
                                .background(color = Color(0xFFC0C0C0).copy(0.20f)),
                            contentScale = ContentScale.Crop,
                        )
                        TopAppBar(
                            title = {
                                if (isSearchVisible.value) {
                                    TextField(
                                        value = searchText.value,
                                        onValueChange = {
                                            searchText.value = it
                                            notesViewModel.searchQuery(it)
                                        },
                                        singleLine = true,
                                        maxLines = 1,
                                        modifier = modifier
                                            .fillMaxWidth()
                                            .focusRequester(focusRequester),
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Done,
                                            keyboardType = KeyboardType.Text
                                        ),
                                        keyboardActions = KeyboardActions(onDone = {
                                            hideKeyboard?.hide()
                                        }),
                                        colors = TextFieldDefaults.textFieldColors(
                                            focusedLabelColor = Color(0xFFFFD300),
                                            focusedIndicatorColor = Color(0xFFFFD300),
                                            cursorColor = Color(0xFFFFD300),
                                            textColor = Color(0xFFFFD300)
                                        )
                                    )
                                } else {
                                    Text(
                                        text = "Notes",
                                        color = Color(0xFFFFD300)
                                    )
                                }
                            },
                            actions = {
                                if (!isSearchVisible.value) {
                                    IconButton(
                                        onClick = {
                                            isSearchVisible.value = true
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "",
                                            tint = Color(0xFFFFD300)
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                bottomSheetState.hide()
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "",
                                            tint = Color(0xFFFFD300)
                                        )
                                    }
                                }
                                if (isSearchVisible.value) {
                                    IconButton(
                                        onClick = {
                                            isSearchVisible.value = false
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "",
                                            tint = Color(0xFFFFD300)
                                        )
                                    }
                                }
                            },
                            backgroundColor = Color.Transparent
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            dialogAddNoteOpened.value = true
                        },
                        backgroundColor = Color(0xFFFFD300)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            tint = Color.Black
                        )
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
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = modifier.padding(top = 24.dp)
                    ) {
                        items(
                            items = notes
                        ) { item ->
                            Card(
                                modifier = modifier
                                    .wrapContentSize()
                                    .padding(12.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(24.dp)
                                    )
                            ) {
                                Box(
                                    modifier = modifier
                                        .fillMaxSize()
                                ) {
                                    if (showDeleteBtn.value) {
                                        Surface(
                                            modifier = modifier
                                                .wrapContentSize()
                                                .align(Alignment.TopEnd)
                                                .clickable {
                                                    savedNote.value = item
                                                    notesViewModel.deleteNote(
                                                        Notes().apply {
                                                            _id = savedNote.value!!._id
                                                        }
                                                    )
                                                    showDeleteBtn.value = false
                                                },
                                            elevation = 24.dp
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "",
                                                tint = Color.Black
                                            )
                                        }
                                    }
                                    Box(
                                        modifier = modifier
                                            .fillMaxWidth()
                                            .height(120.dp)
                                            .padding(12.dp)
                                            .background(Color.Transparent)
                                            .clickable {
                                                savedNote.value = item
                                                dialogUpdateNoteOpened.value = true
                                            }
                                            .pointerInput(Unit) {
                                                detectTapGestures(
                                                    onLongPress = {
                                                        showDeleteBtn.value = !showDeleteBtn.value
                                                    }
                                                )
                                            }
                                    ) {
                                        Text(
                                            text = item.lesson,
                                            color = Color.Black,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                if (dialogAddNoteOpened.value) {
                    Dialog(
                        onDismissRequest = {},
                        properties = DialogProperties(
                            dismissOnBackPress = false,
                            dismissOnClickOutside = false,
                            usePlatformDefaultWidth = false
                        )
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxSize()
                                .background(Color.White)
                        ) {
                            Box(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(Color(0xFF000000))
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.texture_bg),
                                    contentDescription = "",
                                    modifier = modifier
                                        .fillMaxSize()
                                        .background(color = Color(0xFFC0C0C0).copy(0.20f)),
                                    contentScale = ContentScale.Crop,
                                )
                                TopAppBar(
                                    title = {
                                        Text(
                                            text = "New Note",
                                            color = Color(0xFFFFD300)
                                        )
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            if (addNoteText.value.isNotBlank()) {
                                                notesViewModel.insertNote(
                                                    Notes().apply {
                                                        uid = FirebaseAuth.getInstance().uid ?: ""
                                                        note = addNoteText.value
                                                        this.lesson = lesson ?: ""
                                                        time = 0L
                                                    }
                                                )
                                            }
                                            dialogAddNoteOpened.value = false
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "",
                                                tint = Color(0xFFFFD300)
                                            )
                                        }
                                    },
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(top = 12.dp),
                                    backgroundColor = Color.Transparent
                                )
                            }
                            Box(
                                modifier = modifier
                                    .fillMaxSize()
                            ) {
                                Log.d("savedNote", "${savedNote.value?.note}")
                                TextField(
                                    value = addNoteText.value,
                                    onValueChange = { addNoteText.value = it },
                                    modifier = modifier
                                        .fillMaxSize()
                                        .focusRequester(focusRequester),
                                    colors = TextFieldDefaults.textFieldColors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        cursorColor = Color.Black,
                                        textColor = Color.Black,
                                    )
                                )
                            }
                        }
                    }
                }
                if (dialogUpdateNoteOpened.value) {
                    Dialog(
                        onDismissRequest = {},
                        properties = DialogProperties(
                            dismissOnBackPress = false,
                            dismissOnClickOutside = false,
                            usePlatformDefaultWidth = false
                        )
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxSize()
                                .background(Color.White)
                        ) {
                            Box(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(Color(0xFF000000))
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.texture_bg),
                                    contentDescription = "",
                                    modifier = modifier
                                        .fillMaxSize()
                                        .background(color = Color(0xFFC0C0C0).copy(0.20f)),
                                    contentScale = ContentScale.Crop,
                                )
                                TopAppBar(
                                    title = {
                                        Text(
                                            text = "Note",
                                            color = Color(0xFFFFD300)
                                        )
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            notesViewModel.updateNote(
                                                Notes().apply {
                                                    _id = savedNote.value!!._id
                                                    uid = savedNote.value!!.uid
                                                    note = updateNoteText.text
                                                    this.lesson = savedNote.value!!.lesson
                                                    time = savedNote.value!!.time
                                                })
                                            dialogUpdateNoteOpened.value = false
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "",
                                                tint = Color(0xFFFFD300)
                                            )
                                        }
                                    },
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(top = 12.dp),
                                    backgroundColor = Color.Transparent
                                )
                            }
                            Box(
                                modifier = modifier
                                    .fillMaxSize()
                            ) {
                                Log.d("savedNote", "${savedNote.value?.note}")
                                TextField(
                                    value = updateNoteText,
                                    onValueChange = { updateNoteText = it },
                                    modifier = modifier
                                        .fillMaxSize()
                                        .focusRequester(focusRequester),
                                    colors = TextFieldDefaults.textFieldColors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        cursorColor = Color.Black,
                                        textColor = Color.Black,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        },
        content = {
            Scaffold(
                scaffoldState = scaffoldState1,
                topBar = {
                    AnimatedVisibility(
                        visible = isPlayerViewShown.value
                    ) {
                        TopAppBar(
                            modifier = modifier.fillMaxWidth(),
                            backgroundColor = Color.Black
                        ) {
                            Box(modifier = modifier.fillMaxSize()) {
                                Icon(
                                    imageVector = Icons.Default.Notes,
                                    contentDescription = "",
                                    tint = Color.White,
                                    modifier = modifier
                                        .align(Alignment.CenterEnd)
                                        .clickable {
                                            scope.launch {
                                                if (bottomSheetState.isVisible) {
                                                    bottomSheetState.hide()
                                                } else {
                                                    bottomSheetState.show()
                                                }
                                            }
                                        }
                                )
                            }
                        }
                    }
                }
            ) { paddingValues ->
                Box(
                    modifier = modifier.padding(paddingValues)
                ) {
                    Log.d("playerView", "${video}")
                    AndroidView(
                        factory = { context ->
                            PlayerView(context).also {
                                it.player = playerViewModel.player
                                it.setControllerVisibilityListener(PlayerView.ControllerVisibilityListener { visibility ->
                                    isPlayerViewShown.value = visibility == View.VISIBLE
                                })
                            }
                        },
                        update = {
                            when (lifecycle) {
                                Lifecycle.Event.ON_PAUSE -> {
                                    it.onPause()
                                    it.player?.pause()
                                }
                                Lifecycle.Event.ON_RESUME -> {
                                    it.onResume()
                                }
                                else -> Unit
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                if (isLoading) {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            }

        }
    )

}

