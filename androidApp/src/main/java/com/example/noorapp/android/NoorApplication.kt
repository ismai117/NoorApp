package com.example.noorapp.android

import android.app.Application
import com.example.noorapp.android.authentication.forgetPassword.di.forgetPasswordModule
import com.example.noorapp.android.authentication.login.di.loginModule
import com.example.noorapp.android.authentication.register.di.registerModule
import com.example.noorapp.android.authentication.utils.validationModule
import com.example.noorapp.android.collection.collectionModule
import com.example.noor.android.main.discover.di.discoverModule
import com.example.noor.android.main.settings.changePassword.di.changePasswordModule
import com.example.noor.android.main.settings.editProfile.di.editProfileModule
import com.example.noor.android.main.settings.settingsModule
import com.example.noorapp.android.notes.di.userNotesModule
import com.example.noorapp.android.player.playerModule
import com.example.noor.android.user.di.userModule
import com.example.noorapp.auth.authModule
import com.example.noorapp.di.initKoin
import com.example.noorapp.firebase.firebaseModule
import com.example.noorapp.notes.di.notesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger


class NoorApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@NoorApplication)
            modules(
                authModule,
                loginModule,
                registerModule,
                forgetPasswordModule,
                validationModule,
                discoverModule,
                collectionModule,
                playerModule,
                userModule,
                userNotesModule,
                firebaseModule,
                notesModule,
                settingsModule,
                changePasswordModule,
                editProfileModule
            )
        }
    }

}