package com.example.noorapp.android.main.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.noorapp.android.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopBar(
    modifier: Modifier,
    leadingIcon: ImageVector?,
    title: String,
    trailingIcon: ImageVector?,
    leadingIconClicked: () -> Unit,
    trailingIconClicked: () -> Unit,
) {
    TopAppBar(
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
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
            if (leadingIcon != null) {
                Surface(
                    onClick = {
                        leadingIconClicked()
                    },
                    modifier =  modifier.align(Alignment.CenterStart),
                    color = Color.Transparent
                ) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = "",
                        tint = Color(0xFFFFD300),
                    )
                }
            }
            if (trailingIcon != null) {
                Surface(
                    onClick = {
                        trailingIconClicked()
                    },
                    modifier = modifier.align(Alignment.CenterEnd),
                    color = Color.Transparent
                ) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = "",
                        tint = Color(0xFFFFD300),
                    )
                }
            }
        }
    }
}