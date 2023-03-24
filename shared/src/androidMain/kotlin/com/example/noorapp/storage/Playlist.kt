package com.example.noorapp.storage

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class Playlist(
    val name: String,
    val path: String
): Parcelable
