package com.example.listview.database

import com.example.listview.ClassItem
import com.example.listview.StudentItem
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object RealmConfig {
    private const val REALM_FILE_NAME = "students_management.realm"
    private const val REALM_VERSION = 1
    private val encryptionKey = ByteArray(64) { 0x2701.toByte() }
    private val configuration: RealmConfiguration by lazy {
        RealmConfiguration.Builder(schema = setOf(ClassItem::class, StudentItem::class))
            .name(REALM_FILE_NAME)
            .schemaVersion(REALM_VERSION.toLong())
            .encryptionKey(encryptionKey)
            .build()
    }

    fun getRealmInstance(): Realm {
        return Realm.open(configuration)
    }
}

