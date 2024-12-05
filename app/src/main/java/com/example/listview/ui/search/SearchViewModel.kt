package com.example.listview.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listview.StudentItem
import com.example.listview.database.DatabaseHelper
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(realm: Realm): ViewModel() {
    private val databaseHelper = DatabaseHelper(realm)
    private val _searchedStudents = MutableStateFlow<List<StudentItem>>(emptyList())
    val searchedStudents = _searchedStudents.asStateFlow()

    fun searchStudents(keyword: String) {
        viewModelScope.launch {
            databaseHelper.searchStudents(keyword).collect { students ->
                _searchedStudents.value = students
            }
        }
    }
}