package sg.gowild.SplashAwards2k18;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class RecipeActivity extends AppCompatActivity {
    private Button mFindRecipesButton;
    private EditText mIngredient1EditText;
    private EditText mIngredient2EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mFindRecipesButton=(Button)findViewById(R.id.findRecipesButton);
        mIngredient1EditText=(EditText)findViewById(R.id.ingredient1EditText);
        mIngredient2EditText=(EditText)findViewById(R.id.ingredient2EditText);
        mFindRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient1 = mIngredient1EditText.getText().toString();
                String ingredient2 = mIngredient2EditText.getText().toString();
                Intent recipesIntent = new Intent(RecipeActivity.this, RecipeListActivity.class);
                recipesIntent.putExtra("ingredient1", ingredient1);
                recipesIntent.putExtra("ingredient2", ingredient2);
                startActivity(recipesIntent);
            }
        });

    }
}
