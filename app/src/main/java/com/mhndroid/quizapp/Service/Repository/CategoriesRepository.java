package com.mhndroid.quizapp.Service.Repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mhndroid.quizapp.Service.Model.CategoriesModel;

import java.util.List;

public class CategoriesRepository {

    private onFireStoreTaskComplete onFireStoreTaskComplete;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    //declare a reference of Collection Reference
    private CollectionReference collectionReference = firestore.collection("Quiz");


    //constructor for pass the onFireStoreTaskComplete interface , for find the interface method from other class activity
    public CategoriesRepository(CategoriesRepository.onFireStoreTaskComplete onFireStoreTaskComplete) {
        this.onFireStoreTaskComplete = onFireStoreTaskComplete;
    }

    // get categories data via the Collection Reference
    public void getCategoriesData(){
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                   if (task.isSuccessful()){
                       //use onFireStoreTaskComplete interface instance to access the method
                       onFireStoreTaskComplete.categoriesDataLoad(task.getResult().toObjects(CategoriesModel.class));
                   }else{
                       onFireStoreTaskComplete.onError(task.getException());
                   }
            }
        });
    }

    public interface  onFireStoreTaskComplete{
        void categoriesDataLoad(List<CategoriesModel> getData);
        void onError(Exception e);
    }
}
