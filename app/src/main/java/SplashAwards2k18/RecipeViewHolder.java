package SplashAwards2k18;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import SplashAwards2k18.R;


public class RecipeViewHolder extends RecyclerView.ViewHolder {
    private ImageView mRecipeImageView;
    private TextView mRecipeNameTextView;
    private Context mContext;
    private ArrayList<Recipe> mRecipes = new ArrayList<>();

    public RecipeViewHolder(View itemView, ArrayList<Recipe> recipes) {
        super(itemView);
        mRecipeImageView=itemView.findViewById(R.id.recipeImageView);
        mRecipeNameTextView=itemView.findViewById(R.id.recipeNameTextView);
        mContext = itemView.getContext();
        mRecipes = recipes;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra("recipe", Parcels.wrap(mRecipes.get(itemPosition)));
                mContext.startActivity(intent);
            }
        });
    }

    public void bindRecipe(Recipe recipe) {
        Picasso.with(mContext).load(recipe.getImageUrl()).into(mRecipeImageView);
        mRecipeNameTextView.setText(recipe.getName());
    }
}