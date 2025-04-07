package com.nvshink.winterarc.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nvshink.winterarc.data.repository.ExerciseRepository
import com.nvshink.winterarc.data.room.WinterArcDatabase

//open class ExerciseViewModelFactory(private val repository: ExerciseRepository) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
//            return ExerciseViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}