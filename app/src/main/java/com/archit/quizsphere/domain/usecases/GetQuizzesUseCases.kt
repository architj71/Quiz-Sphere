package com.archit.quizsphere.domain.usecases

import com.archit.quizsphere.common.Resource
import com.archit.quizsphere.domain.model.Quiz
import com.archit.quizsphere.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetQuizzesUseCases(val quizRepository: QuizRepository) {

    operator fun invoke(amount: Int,
                   category: Int,
                   difficulty: String,
                   type: String
    ): kotlinx.coroutines.flow.Flow<Resource<List<Quiz>>> = flow {

        emit(Resource.Loading())

        try {
            emit(Resource.Success(data = quizRepository.getQuizzes(amount, category, difficulty, type)))
        }catch (e: Exception){
            emit(Resource.Error(message = e.message.toString()))
        }


    }.flowOn(Dispatchers.IO)

}