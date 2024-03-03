package com.example.quizapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapp.model.QuestionList
import com.example.quizapp.retrofit.QuestionAPI
import com.example.quizapp.retrofit.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.create

class QuizRepository {

    private var questionAPI : QuestionAPI

    init {
        questionAPI = RetrofitInstance().getRetrofitInstance().create(QuestionAPI::class.java)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getQuestionsFromAPI():LiveData<QuestionList>{
        //Live Data
        val data = MutableLiveData<QuestionList>()
        var questionList : QuestionList

        GlobalScope.launch(Dispatchers.IO) {
            //returning the response
            val response = questionAPI.getQuestion()

            //saving the data to list
            questionList = response.body()!!

            data.postValue(questionList)
            Log.i("TAGY",""+data.value)
        }
        return data
    }
}