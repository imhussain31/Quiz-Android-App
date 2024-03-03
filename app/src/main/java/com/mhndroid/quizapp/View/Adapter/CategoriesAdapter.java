package com.mhndroid.quizapp.View.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mhndroid.quizapp.R;
import com.mhndroid.quizapp.Service.Model.CategoriesModel;
import com.mhndroid.quizapp.View.UI.QuizActivity;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private List<CategoriesModel> all_quiz_categories;



    public void setAll_quiz_categories(List<CategoriesModel> all_quiz_categories ) {
        this.all_quiz_categories = all_quiz_categories;
    }


    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_single_row_design, parent , false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, @SuppressLint("RecyclerView") int position) {
          CategoriesModel model = all_quiz_categories.get(position);
          holder.title.setText(model.getTitle());
          //Glide.with(holder.itemView).load(model.getImgUrl()).into(holder.categoriesImage);

        // Assuming you have different drawable resources for each position
        int drawableResId = getDrawableResourceForPosition(position);

        // Set the drawable resource to the ImageView
        holder.categoriesImage.setImageResource(drawableResId);

        // Local variables for this specific item
          final String quizId = model.getQuizId();
          final long totalQuesionNumber = model.getQuestions();

          holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), QuizActivity.class);
                // Pass the position and QuizId as extras
                intent.putExtra("position", position);
                intent.putExtra("quizId", quizId);
                intent.putExtra("totalQ", totalQuesionNumber);
                view.getContext().startActivity(intent);
            }
         });
    }

    //define a method for specific categories list icon
    private int getDrawableResourceForPosition(int position) {
        switch (position) {
            case 0:
                return R.drawable.math;
            case 1:
                return R.drawable.physics;
            default:
                return R.drawable.chemistry;
        }
    }




    @Override
    public int getItemCount() {
        if (all_quiz_categories == null){
                   return 0;
        }else{
            return all_quiz_categories.size();
        }

    }

    //declare categories_single_row_design View instances and find them to implement
    class CategoriesViewHolder extends RecyclerView.ViewHolder  {
        ImageView categoriesImage;
        TextView title;
        CardView cardView;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            categoriesImage = itemView.findViewById(R.id.singleRowImgViewId);
            title = itemView.findViewById(R.id.singleRowTitleId);

            cardView = itemView.findViewById(R.id.CategoriesCardViewId);


        }

    }
}
