package SplashAwards2k18;

import com.google.firebase.database.Exclude;

import org.json.JSONObject;
import org.parceler.Parcel;


@Parcel
public class Recipe {
    private String name;
    private String imageUrl;
    private String sourceUrl;
    private String[] ingredients;
    private String pushId;
    private Calories calories;

    public Recipe() {};

    public Recipe(String name, String imageUrl, String sourceUrl, String ingredients, JSONObject cals) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.sourceUrl = sourceUrl;
        this.ingredients = ingredients.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\\\","").split("\",\"");
        this.calories=new Calories(cals);
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }
    @Exclude
    public String[] getIngredients() {
        return ingredients;
    }

    public String getPushId() {
        return pushId;
    }

    @Exclude
    public String getCalories(){return calories.toString();}

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}