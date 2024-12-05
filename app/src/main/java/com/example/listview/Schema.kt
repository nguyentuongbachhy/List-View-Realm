package com.example.listview

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class ClassItem() : RealmObject {
    @PrimaryKey var id: String = ""
    var name: String = ""

    constructor(id: String, name: String) : this() {
        this.id = id
        this.name = name
    }

}

open class StudentItem(): RealmObject {
    @PrimaryKey var id: String = ""
    var fullName: String = ""
    var birthday: String = "01/01/2000"
    var gender: Int = 1
    var classId: String = ""

    constructor(id: String, fullName: String, birthday: String, gender: Int, classId: String) : this() {
        this.id = id
        this.fullName = fullName
        this.birthday = birthday
        this.gender = gender
        this.classId = classId
    }
}

