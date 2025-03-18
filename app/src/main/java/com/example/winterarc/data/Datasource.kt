package com.example.winterarc.data

import com.example.winterarc.data.model.Exercise

//import com.example.affirmations.R
//import com.example.affirmations.model.Affirmation

/**
 * [Datasource] generates a list of [Affirmation]
 */
class Datasource() {
    fun loadExercises(): List<Exercise> {
        return listOf<Exercise>(
            Exercise("Ажумания", listOf(""), "жум жум")
        )
    }
}