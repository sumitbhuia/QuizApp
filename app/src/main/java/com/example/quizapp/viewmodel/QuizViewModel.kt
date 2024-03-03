package com.example.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.model.QuestionList
import com.example.quizapp.repository.QuizRepository

class QuizViewModel: ViewModel() {
    var repository : QuizRepository = QuizRepository()
    lateinit var questionLiveData : LiveData<QuestionList>

    init {
        questionLiveData = repository.getQuestionsFromAPI()
    }

    fun getQuestionsFromLiveData():LiveData<QuestionList>{
        return questionLiveData
    }
}