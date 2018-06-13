package sg.gowild.SplashAwards2k18;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private ArrayList<Recipe> mRecipes = new ArrayList<>();
    private Context mContext;

    public RecipeListAdapter(Context context, ArrayList<Recipe> recipes) {
        mContext = context;
        mRecipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view, mRecipes);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bindRecipe(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }



}
