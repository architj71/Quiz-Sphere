package com.archit.quizsphere.data.repository

import android.util.Log
import com.archit.quizsphere.data.remote.QuizApi
import com.archit.quizsphere.domain.model.Quiz
import com.archit.quizsphere.domain.repository.QuizRepository

class QuizRepositoryImpl(
    private val quizApi: QuizApi
) : QuizRepository {
    override suspend fun getQuizzes(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String,
    ): List<Quiz> {

        val rsp = quizApi.getQuizzes(amount, category, difficulty, type).results
        Log.d("quiz", rsp.toString())
        return rsp


    }
}