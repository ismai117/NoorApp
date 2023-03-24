package com.example.noorapp.notes.domain

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class Notes : RealmObject{
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var uid : String = ""
    var note: String = ""
    var lesson: String = ""
    var time: Long = 0L
}