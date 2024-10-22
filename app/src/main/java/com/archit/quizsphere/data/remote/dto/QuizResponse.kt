package com.archit.quizsphere.data.remote.dto

import com.archit.quizsphere.domain.model.Quiz

data class QuizResponse(
    val response_code: Int,
    val results: List<Quiz>
)