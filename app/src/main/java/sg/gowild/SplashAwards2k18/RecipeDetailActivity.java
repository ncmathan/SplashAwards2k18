package sg.gowild.SplashAwards2k18;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;


public class RecipeDetailActivity extends AppCompatActivity {
    //@Bind(R.id.viewPager) ViewPager mViewPager;
    //private RecipePagerAdapter adapterViewPager;
    private ImageView mImageLabel;
    private TextView mNameLabel;
    private TextView mWebsiteLabel;
    private ListView mIngredientList;
    private Button mSaveRecipeButton;
    private SharedPreferences mSharedPreferences;


    private Recipe mRecipe;
    private ArrayList<Recipe> mRecipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        mRecipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        mImageLabel=(ImageView)findViewById(R.id.recipeImageView);
        mNameLabel=(TextView)findViewById(R.id.recipeNameTextView);
        mWebsiteLabel=(TextView)findViewById(R.id.websiteTextView);
        mIngredientList=(ListView)findViewById(R.id.ingredientListView);
        mSaveRecipeButton=(Button)findViewById(R.id.saveRecipeButton);

        ArrayAdapter ingredientAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mRecipe.getIngredients());
        mIngredientList.setAdapter(ingredientAdapter);
        Picasso.with(this).load(mRecipe.getImageUrl()).into(mImageLabel);
        mNameLabel.setText(mRecipe.getName());
        mWebsiteLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mRecipe.getSourceUrl()));
                startActivity(webIntent);
            }
        });
        mSaveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
