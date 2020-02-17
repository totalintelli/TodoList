package com.example.todolist

import android.app.Application
import io.realm.Realm

// Realm 문서 링크 : https://realm.io/kr/docs/
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}