package com.example.noorapp.android.collection

import com.example.noor.android.collection.CollectionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val collectionModule = module {
    viewModel { CollectionViewModel(get()) }
}