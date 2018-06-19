package SplashAwards2k18;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.DisplayContext;
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
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import SplashAwards2k18.R;


public class RecipeDetailActivity extends AppCompatActivity {
    //@Bind(R.id.viewPager) ViewPager mViewPager;
    //private RecipePagerAdapter adapterViewPager;
    public static ArrayList<Recipe> recipes;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();

    private ImageView mImageLabel;
    private TextView mNameLabel;
    private TextView mWebsiteLabel;
    private ListView mIngredientList;
    private Button mSaveRecipeButton;

    private TextView calInfoBtn;
    private TextView calInfo;
    private WebView recipeweb;
    private Button addCalories;
    private LinearLayout mainView;
    private SharedPreferences mSharedPreferences;

    private boolean favexists;
    private RecipeDetailActivity a=this;


    private Recipe mRecipe;
    private ArrayList<Recipe> mRecipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        mSharedPreferences=getSharedPreferences("fav",0);
        favexists=false;
        mRecipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        mImageLabel=(ImageView)findViewById(R.id.recipeImageView);
        mNameLabel=(TextView)findViewById(R.id.recipeNameTextView);
        mWebsiteLabel=(TextView)findViewById(R.id.websiteTextView);
        mIngredientList=(ListView)findViewById(R.id.ingredientListView);
        mSaveRecipeButton=(Button)findViewById(R.id.saveRecipeButton);

        calInfoBtn=(TextView)findViewById(R.id.nutritionTextView);
        calInfo=(TextView)findViewById(R.id.nutritionInfo);
        recipeweb=(WebView)findViewById(R.id.recipeweb);
        recipeweb.getSettings().setJavaScriptEnabled(true);
        recipeweb.setWebViewClient(new WebViewController());
        addCalories=(Button)findViewById(R.id.AddCaloriesButton);
        mainView=(LinearLayout)findViewById(R.id.mainRecipeDetail);


        ArrayAdapter ingredientAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mRecipe.getIngredients());
        mIngredientList.setAdapter(ingredientAdapter);
        mIngredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dbRef.child("shoppinglist").push().setValue(mIngredientList.getItemAtPosition(position).toString());
                Toast.makeText(a, "Recipe added to favourites!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        Picasso.with(this).load(mRecipe.getImageUrl()).into(mImageLabel);
        mNameLabel.setText(mRecipe.getName());
        mWebsiteLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeweb.loadUrl(mRecipe.getSourceUrl());
                recipeweb.setVisibility(View.VISIBLE);
                mainView.setVisibility(View.INVISIBLE);
            }
        });
        calInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calInfo.setVisibility(View.VISIBLE);
                mainView.setVisibility(View.INVISIBLE);
                calInfo.setText(mRecipe.getCalories());
            }
        });
        mSaveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipes!=null&&recipes.contains(mRecipe)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(a);
                    builder.setMessage("This recipe already exists!")
                            .setTitle("Error").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if(recipes==null){
                    recipes=new ArrayList<>();
                }
                else{
                    recipes.add(mRecipe);
                    Toast.makeText(a, "Recipe added to favourites!",
                            Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(recipeweb.getVisibility()==View.VISIBLE){
            recipeweb.setVisibility(View.INVISIBLE);
            mainView.setVisibility(View.VISIBLE);
        }
        else if(calInfo.getVisibility()==View.VISIBLE){
            calInfo.setVisibility(View.INVISIBLE);
            mainView.setVisibility(View.VISIBLE);
        }
        else{
        super.onBackPressed();}
    }
}
