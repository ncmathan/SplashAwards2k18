package SplashAwards2k18;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.parceler.Parcels;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import SplashAwards2k18.RecipeListActivity;
import SplashAwards2k18.R;


public class RecipeActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();

    private Button mFindRecipesButton;
    private EditText ingredients;
    private EditText minCals;
    private EditText maxCals;
    private Spinner health;
    private Spinner diet;
    private Button viewFavourites;
    RecipeActivity a=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mFindRecipesButton=(Button)findViewById(R.id.findRecipesButton);
        ingredients=(EditText)findViewById(R.id.ingredient1EditText);
        minCals=(EditText)findViewById(R.id.calmin);
        maxCals=(EditText)findViewById(R.id.calmax);
        health=(Spinner)findViewById(R.id.spinner1);
        diet=(Spinner)findViewById(R.id.spinner2);
        viewFavourites=(Button)findViewById(R.id.viewFavouriteButton);
        int num=getIntent().getIntExtra("num",0);
        open(num);
        viewFavourites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent recipesIntent = new Intent(RecipeActivity.this, RecipeListActivity.class);
                recipesIntent.putExtra("favourite",Parcels.wrap(RecipeDetailActivity.recipes));
                recipesIntent.putExtra("hasFav",true);
                startActivity(recipesIntent);
            }
        });
        String[] healthLabels = new String[]{"Health options", "alcohol-free", "celery-free","crustacean-free","dairy-free","egg-free","fish-free","gluten-free","kidney-friendly","kosher","low-potassium","lupine-free","mustard-free","No-oil-added","low-sugar","paleo","peanut-free","pescatarian","pork-free","red-meat-free","sesame-free","shellfish-free","soy-free","sugar-conscious","tree-nut-free","vegan","vegetarian","wheat-free"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, healthLabels);
        //set the spinners adapter to the previously created one.
        health.setAdapter(adapter);

        String[] dietLabels = new String[]{"Diet options", "balanced", "high-fiber","high-protein","low-carb","low-fat","low-sodium"};
        ArrayAdapter<String> dadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dietLabels);
        //set the spinners adapter to the previously created one.
        diet.setAdapter(dadapter);
        mFindRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient =ingredients.getText().toString();
                if(ingredient.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(a);
                    builder.setMessage("Please enter an ingredient or food item!")
                            .setTitle("Input error").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                String calmin=minCals.getText().toString();
                String calmax=maxCals.getText().toString();
                String hlabel=health.getSelectedItem().toString();
                String dlabel=diet.getSelectedItem().toString();
                if(hlabel.equals("Health options")){hlabel="";}
                if(dlabel.equals("Diet options")){dlabel="";}
                Intent recipesIntent = new Intent(RecipeActivity.this, RecipeListActivity.class);
                recipesIntent.putExtra("ingredient", ingredient);
                recipesIntent.putExtra("calmin", calmin);
                recipesIntent.putExtra("calmax", calmax);
                recipesIntent.putExtra("hlabel", hlabel);
                recipesIntent.putExtra("dlabel", dlabel);
                recipesIntent.putExtra("hasFav",false);
                startActivity(recipesIntent);}
            }
        });

    }
    public void open(int num){
        if(num==1){
            Intent recipesIntent = new Intent(RecipeActivity.this, RecipeListActivity.class);
            recipesIntent.putExtra("favourite",Parcels.wrap(RecipeDetailActivity.recipes));
            recipesIntent.putExtra("hasFav",true);
            startActivity(recipesIntent);
        }
    }
}
