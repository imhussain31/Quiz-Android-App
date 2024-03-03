package com.mhndroid.quizapp.Service.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mhndroid.quizapp.Service.Model.QuestionModel;

import java.util.HashMap;
import java.util.List;

public class QuestionRepository {
    private FirebaseFirestore firestore;
    private String quizId;

    private onQuestionLoad onQuestionLoad;


    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public QuestionRepository(onQuestionLoad onQuestionLoad){
        firestore = FirebaseFirestore.getInstance();
        this.onQuestionLoad = onQuestionLoad;

    }

    // get categories data via the Collection Reference
    public void getQuestion(){
        firestore.collection("Quiz").document(quizId).collection("questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        //use onFireStoreTaskComplete interface instance to access the method
                        onQuestionLoad.onLoad(task.getResult().toObjects(QuestionModel.class));
                    }else{
                        onQuestionLoad.onError(task.getException());
                    }
            }
        });
    }


    // Define an interface for handling the list of QuestionModel objects.
    public interface onQuestionLoad{
        // Implement this method to handle the loaded list of QuestionModel objects.
        void onLoad(List<QuestionModel> list);

        // Implement this method to handle and respond to the occured exception.
        void onError(Exception e);
    }


}
