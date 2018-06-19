package SplashAwards2k18;


import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import SplashAwards2k18.RecipeListAdapter;
import SplashAwards2k18.R;

public class RecipeListActivity extends AppCompatActivity {
    public static final String TAG = RecipeListActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecipeListAdapter mAdapter;

    public ArrayList<Recipe> mRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        Intent recipesIntent = getIntent();
        String val=recipesIntent.getStringExtra("string");
        if(val!=null){
            open(val);
        }
        else{if(!recipesIntent.getBooleanExtra("hasFav",false)){
        String ingredient = recipesIntent.getStringExtra("ingredient");
        String calmin = recipesIntent.getStringExtra("calmin");
        String calmax = recipesIntent.getStringExtra("calmax");
        String hlabel = recipesIntent.getStringExtra("hlabel");
        String dlabel = recipesIntent.getStringExtra("dlabel");

        getRecipes(ingredient,calmin,calmax,hlabel,dlabel);}
        else{
            mRecipes= Parcels.unwrap(getIntent().getParcelableExtra("favourite"));
            RecipeListActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter = new RecipeListAdapter(getApplicationContext(), mRecipes);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipeListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                }
            });
        }}
    }
    public void open(String ingredient){
        getRecipes(ingredient,"","","","");
    }

    private void getRecipes(String ingredient,String calmin,String calmax,String hlabel,String dlabel) {
        findRecipes(ingredient,calmin,calmax,hlabel,dlabel, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mRecipes = processResults(response);


                RecipeListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new RecipeListAdapter(getApplicationContext(), mRecipes);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipeListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });

            }
        });
    }
    public static void findRecipes(String ingredient,String calmin,String calmax,String hlabel,String dlabel, Callback callback) {
        String APP_ID = "8761e9d8";
        String APP_KEY = "75e3e58ffc0d7af104af186638838524";
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        String ingredients = ingredient.replaceAll("\\s","%20");
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.edamam.com/search").newBuilder();
        urlBuilder.addQueryParameter("q", ingredients);
        urlBuilder.addQueryParameter("app_id", APP_ID);
        urlBuilder.addQueryParameter("app_key", APP_KEY);
        if(!calmin.isEmpty() && calmax.isEmpty()){
            urlBuilder.addQueryParameter("calories", calmin+"%2B");
        }
        else if(calmin.isEmpty() && !calmax.isEmpty()){
            urlBuilder.addQueryParameter("calories", calmax);
        }
        else if(!calmin.isEmpty() && !calmax.isEmpty()){
            urlBuilder.addQueryParameter("calories", calmin+"-"+calmax);
        }
        if(!hlabel.isEmpty()){
            urlBuilder.addQueryParameter("health", hlabel);
        }
        if(!dlabel.isEmpty()){
            urlBuilder.addQueryParameter("diet", dlabel);
        }
        String url = urlBuilder.build().toString();
        Log.v(TAG, url);
        System.out.println("Website url:\n"+url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    public ArrayList<Recipe> processResults(Response response) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject returnJSON = new JSONObject(jsonData);
                JSONArray recipesJSON = returnJSON.getJSONArray("hits");
                for (int i = 0; i < 30; i++) {
                    JSONObject recipeArrayJSON = recipesJSON.getJSONObject(i);
                    JSONObject recipeJSON = recipeArrayJSON.getJSONObject("recipe");
                    String name = recipeJSON.getString("label");
                    String imageUrl = recipeJSON.getString("image");
                    String sourceUrl = recipeJSON.getString("url");
                    String ingredients = recipeJSON.getJSONArray("ingredientLines").toString();

                    Recipe recipe = new Recipe (name, imageUrl, sourceUrl, ingredients,recipeJSON);
                    recipes.add(recipe);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }


}
