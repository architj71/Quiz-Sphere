package com.archit.quizsphere.domain.repository

import com.archit.quizsphere.domain.model.Quiz

interface QuizRepository {

    suspend fun getQuizzes(amount: Int, category: Int, difficulty: String, type: String) : List<Quiz>
}