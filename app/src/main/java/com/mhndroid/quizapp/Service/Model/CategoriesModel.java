package com.mhndroid.quizapp.Service.Model;

import com.google.firebase.firestore.DocumentId;

public class CategoriesModel {

    //Set getter and setter method to fatching the data from firebase according to the field/instance
    @DocumentId
    private String quizId;
    private String title , imgUrl ;
    private long questions;

    public CategoriesModel(){

    }
    public CategoriesModel(String title, String imgUrl, String quizId, long questions) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.quizId = quizId;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public long getQuestions() {
        return questions;
    }

    public void setQuestions(long questions) {
        this.questions = questions;
    }
}
