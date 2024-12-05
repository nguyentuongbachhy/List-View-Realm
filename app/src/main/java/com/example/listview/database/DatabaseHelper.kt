package com.example.listview.database

import com.example.listview.ClassItem
import com.example.listview.StudentItem
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseHelper(private val realm: Realm) {

    fun getClasses(): Flow<List<ClassItem>> {
        return realm.query<ClassItem>().asFlow().map { it.list }
    }

    suspend fun addClass(classItem: ClassItem) {
        realm.write {
            copyToRealm(classItem)
        }
    }

    fun isExistClassSync(classId: String): Boolean {
        return realm.query<ClassItem>("id = $0", classId).first().find() != null
    }

    suspend fun deleteClassById(classId: String) {
        realm.write {
            val classItem = query<ClassItem>("id = $0", classId).first().find()
            if (classItem != null) {
                delete(classItem)
            }
        }
    }

    fun getClassIds(): Flow<List<String>> {
        return realm.query<ClassItem>().asFlow().map { results ->
            results.list.map { it.id }
        }
    }

    fun getStudentsByClass(classId: String): Flow<List<StudentItem>> {
        return realm.query<StudentItem>("classId = $0", classId).asFlow().map { results ->
            results.list
        }
    }

    fun getStudentById(studentId: String): StudentItem? {
        return realm.query<StudentItem>("id = $0", studentId).first().find()
    }

    suspend fun addStudent(student: StudentItem) {
        realm.write {
            copyToRealm(student)
        }
    }

    suspend fun updateStudent(student: StudentItem) {
        realm.write {
            val existingStudent = query<StudentItem>("id = $0", student.id).first().find()
            if (existingStudent != null) {
                existingStudent.fullName = student.fullName
                existingStudent.birthday = student.birthday
                existingStudent.gender = student.gender
                existingStudent.classId = student.classId
            }
        }
    }

    suspend fun deleteStudentById(studentId: String) {
        realm.write {
            val student = query<StudentItem>("id = $0", studentId).first().find()
            if (student != null) {
                delete(student)
            }
        }
    }

    fun searchStudents(keyword: String): Flow<List<StudentItem>> {
        return realm.query<StudentItem>("fullName CONTAINS[c] $0", keyword).asFlow().map { results ->
            results.list
        }
    }
}
