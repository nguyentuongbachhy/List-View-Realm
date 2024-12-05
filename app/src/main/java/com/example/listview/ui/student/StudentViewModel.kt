package com.example.listview.ui.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listview.StudentItem
import com.example.listview.database.DatabaseHelper
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StudentViewModel(realm: Realm, private val className: String) : ViewModel() {
    private val databaseHelper = DatabaseHelper(realm)
    private val _students = MutableStateFlow<List<StudentItem>>(emptyList())
    private val _classIds = MutableStateFlow<List<String>>(emptyList())
    val students = _students.asStateFlow()
    val classIds = _classIds.asStateFlow()

    init {
        fetchStudentsByClass(className)
        fetchClassId()
    }

    private fun fetchStudentsByClass(classId: String) {
        viewModelScope.launch {
            databaseHelper.getStudentsByClass(classId).collectLatest {studentList ->
                _students.value = studentList
            }
        }
    }

    fun fetchClassId() {
        viewModelScope.launch {
            databaseHelper.getClassIds().collectLatest {classIdList ->
                _classIds.value = classIdList
            }
        }
    }

    fun addStudent(student: StudentItem) {
        viewModelScope.launch {
            databaseHelper.addStudent(student)
            fetchStudentsByClass(student.classId)
        }
    }

    fun updateStudent(student: StudentItem) {
        viewModelScope.launch {
            databaseHelper.updateStudent(student)
            fetchStudentsByClass(student.classId)
        }
    }

    fun deleteStudent(studentId: String) {
        viewModelScope.launch {
            databaseHelper.deleteStudentById(studentId)
            fetchStudentsByClass(className)
        }
    }

    fun getStudentById(studentId: String): StudentItem? {
        if(studentId.isEmpty()) {
            return StudentItem()
        }
        return databaseHelper.getStudentById(studentId)
    }
}