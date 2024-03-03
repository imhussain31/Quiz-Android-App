package com.mhndroid.quizapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mhndroid.quizapp.Service.Model.QuestionModel;
import com.mhndroid.quizapp.Service.Repository.QuestionRepository;

import java.util.HashMap;
import java.util.List;

public class QuestionViewModel extends ViewModel implements QuestionRepository.onQuestionLoad {

    // MutableLiveData to store and observe the list of QuestionModel.
    private MutableLiveData<List<QuestionModel>> qmutableLiveData;

    // Instance of QuestionRepository to fetch Question data.
    QuestionRepository questionRepository;


    // Constructor initializes the MutableLiveData and QuestionRepository.
    public MutableLiveData<List<QuestionModel>> getQmutableLiveData() {
        return qmutableLiveData;
    }

    // Constructor initializes the MutableLiveData and QuestionRepository.
    public QuestionViewModel(){
        qmutableLiveData = new MutableLiveData<>();
        questionRepository = new QuestionRepository(this);
    }


    // Method to set the quiz ID and fetch questions.
    public void setQuizId(String setquizId){
        questionRepository.setQuizId(setquizId);
        questionRepository.getQuestion();
    }

    // Callback method when questions are successfully loaded.
    @Override
    public void onLoad(List<QuestionModel> list) {
         qmutableLiveData.setValue(list);
    }

    // Callback method to handle errors during the question load.
    @Override
    public void onError(Exception e) {
        Log.d("Error" , "QuizError" + e.getMessage());
    }
}
