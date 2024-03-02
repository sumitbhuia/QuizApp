package com.example.quizapp.retrofit

import com.example.quizapp.model.QuestionList
import com.example.quizapp.model.QuestionListItem
import retrofit2.Response
import retrofit2.http.GET
import java.net.ResponseCache

interface QuestionAPI {

    @GET("questionsapi.php")
    suspend fun getQuestion():Response<QuestionList>


}