package com.example.quizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.model.QuestionList
import com.example.quizapp.model.QuestionListItem
import com.example.quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var quizViewModel: QuizViewModel
    lateinit var questionList: List<QuestionListItem>

    companion object{
        var result = 0
        var totalQuestions = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main)

        //Resetting the quiz
        result =0
        totalQuestions = 0

        // Getting the response from retrofit
        quizViewModel = ViewModelProvider(this)[QuizViewModel::class.java]

        // Displaying th first question
        GlobalScope.launch (Dispatchers.Main){
            quizViewModel.getQuestionsFromLiveData().observe(this@MainActivity) {
                if (it.size > 0) {
                    questionList = it
                    Log.i("TAGY" , "This is the first question : ${questionList[0]}")

                    binding.apply {
                        txtQuestion.text = questionList[0].question
                        radio1.text = questionList[0].option1
                        radio2.text = questionList[0].option2
                        radio3.text = questionList[0].option3
                        radio4.text = questionList[0].option4
                    }
                }
            }
        }
        // Adding functionalities fro the next button
        var i=1
        binding.apply {
            nextButton.setOnClickListener {
                val selectedOption = radioGroup.checkedRadioButtonId
                if (selectedOption!=-1){
                    val radButton = findViewById<View>(selectedOption)as RadioButton

                    questionList.let {
                        if (i<it.size!!){
                            //Getting number of questions
                            totalQuestions = it.size
                            //check if answer correct
                            if (radButton.text.toString().equals(it[i-1].correct_option)) {
                                result++
                                txtCorrectOption.text = "Correct Answer : $result"
                            }

                            //Displaying next question
                            txtQuestion.text = "Question ${i+1} : "+it[i].question
                            radio1.text = it[i].option1
                            radio2.text = it[i].option2
                            radio3.text = it[i].option3
                            radio4.text = it[i].option4

                            //Checking if it is last question
                            if(i==it.size!!.minus(1)){
                                nextButton.text = "Finish"

                            }

                            radioGroup.clearCheck()
                            i++
                        }
                        else {
                            if (radButton.text.toString().equals(it[i-1].correct_option)){
                                result++
                                txtCorrectOption.text = "Correct Answer : $result"
                            }
                            val intent = Intent(this@MainActivity,ResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    
                }
                else{
                    Toast.makeText(this@MainActivity,"Oppsie !! Empty field .",Toast.LENGTH_LONG).show()
                }
            }
        }






    }
}