package SplashAwards2k18;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Calories {
    private double calories=0;
    private double fat=0;
    private double saturatedfat=0;
    private double transfat=0;
    private double monosaturatedfat=0;
    private double polysaturatedfat=0;
    private double carbs=0;
    private double fiber=0;
    private double sugars=0;
    private double protein=0;
    private double cholestrol=0;
    private double sodium=0;
    private double calcium=0;
    private double magnesium=0;
    private double potassium=0;
    private double iron=0;
    private double zinc=0;
    private double phosphorous=0;
    private double vitamina=0;
    private double vitaminc=0;
    private double b1=0;
    private double b2=0;
    private double b3=0;
    private double b6=0;
    private double b12=0;
    private double vitamind=0;
    private double folate=0;
    private double vitamine=0;
    private double vitamink=0;

    public static double Scalories=2000;
    public static double Sfat=70;
    public static double Ssaturatedfat=24;
    public static double Scarbs=300;
    public static double Sfiber=25;
    public static double Ssugars=90;
    public static double Sprotein=50;
    public static double Scholestrol=300;
    public static double Ssodium=2300;
    public static double Scalcium=995;
    public static double Smagnesium=400;
    public static double Spotassium=3500;
    public static double Siron=18;
    public static double Szinc=15;
    public static double Sphosphorous=700;
    public static double Svitamina=900;
    public static double Svitaminc=60;
    public static double Sb1=(double)1.5;
    public static double Sb2=(double)1.7;
    public static double Sb3=20;
    public static double Sb6=2;
    public static double Sb12=6;
    public static double Svitamind=400;
    public static double Sfolate=400;
    public static double Svitamine=400;
    public static double Svitamink=80;

    private double weight=0;

    public Calories(JSONObject json){
        try {

            weight = json.getDouble("totalWeight");
            JSONObject nutrients=json.getJSONObject("totalNutrients");
            calories=nutrients.getJSONObject("ENERC_KCAL").getDouble("quantity");
            if(nutrients.has("FAT")){
                fat=nutrients.getJSONObject("FAT").getDouble("quantity");
            }
            if(nutrients.has("FASAT")){
                saturatedfat=nutrients.getJSONObject("FASAT").getDouble("quantity");
            }
            if(nutrients.has("FATRN")){
                transfat=nutrients.getJSONObject("FATRN").getDouble("quantity");
            }
            if(nutrients.has("FAMS")){
                monosaturatedfat=nutrients.getJSONObject("FAMS").getDouble("quantity");
            }
            if(nutrients.has("FAPU")){
                polysaturatedfat=nutrients.getJSONObject("FAPU").getDouble("quantity");
            }
            if(nutrients.has("CHOCDF")){
                carbs=nutrients.getJSONObject("CHOCDF").getDouble("quantity");
            }
            if(nutrients.has("FIBTG")){
                fiber=nutrients.getJSONObject("FIBTG").getDouble("quantity");
            }
            if(nutrients.has("SUGAR")){
                sugars=nutrients.getJSONObject("SUGAR").getDouble("quantity");
            }
            if(nutrients.has("SUGAR.added")){
                sugars+=nutrients.getJSONObject("SUGAR.added").getDouble("quantity");
            }
            if(nutrients.has("PROCNT")){
                fiber=nutrients.getJSONObject("PROCNT").getDouble("quantity");
            }
            if(nutrients.has("CHOLE")){
                cholestrol=nutrients.getJSONObject("CHOLE").getDouble("quantity");
            }
            if(nutrients.has("NA")){
                sodium=nutrients.getJSONObject("NA").getDouble("quantity");
            }
            if(nutrients.has("CA")){
                calcium=nutrients.getJSONObject("CA").getDouble("quantity");
            }
            if(nutrients.has("MG")){
                magnesium=nutrients.getJSONObject("MG").getDouble("quantity");
            }
            if(nutrients.has("K")){
                potassium=nutrients.getJSONObject("K").getDouble("quantity");
            }
            if(nutrients.has("FE")){
                iron=nutrients.getJSONObject("FE").getDouble("quantity");
            }
            if(nutrients.has("ZN")){
                zinc=nutrients.getJSONObject("ZN").getDouble("quantity");
            }
            if(nutrients.has("P")){
                phosphorous=nutrients.getJSONObject("P").getDouble("quantity");
            }
            if(nutrients.has("VITA_RAE")){
                vitamina=nutrients.getJSONObject("VITA_RAE").getDouble("quantity");
            }
            if(nutrients.has("VITC")){
                vitaminc=nutrients.getJSONObject("VITC").getDouble("quantity");
            }
            if(nutrients.has("THIA")){
                b1=nutrients.getJSONObject("THIA").getDouble("quantity");
            }
            if(nutrients.has("RIBF")){
                b2=nutrients.getJSONObject("RIBF").getDouble("quantity");
            }
            if(nutrients.has("NIA")){
                b3=nutrients.getJSONObject("NIA").getDouble("quantity");
            }
            if(nutrients.has("VITB6")){
                b6=nutrients.getJSONObject("VITB6A").getDouble("quantity");
            }
            if(nutrients.has("FOLDFE")){
                folate=nutrients.getJSONObject("FOLDFE").getDouble("quantity");
            }
            if(nutrients.has("VITB12")){
                b12=nutrients.getJSONObject("VITB12").getDouble("quantity");
            }
            if(nutrients.has("VITK1")){
                vitamink=nutrients.getJSONObject("VITK1").getDouble("quantity");
            }
            if(nutrients.has("VITD")){
                vitamind=nutrients.getJSONObject("VITD").getDouble("quantity");
            }
            if(nutrients.has("VITE")){
                vitamine=nutrients.getJSONObject("VITE").getDouble("quantity");
            }

        }  catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public double getTransfat() {
        return transfat;
    }

    public double getCholestrol() {
        return cholestrol;
    }

    public double getB12() {
        return b12;
    }

    public double getVitamind() {
        return vitamind;
    }

    public double getCalories() {
        return calories;
    }

    public double getFat() {
        return fat;
    }

    public double getSaturatedfat() {
        return saturatedfat;
    }

    public double getMonosaturatedfat() {
        return monosaturatedfat;
    }

    public double getPolysaturatedfat() {
        return polysaturatedfat;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFiber() {
        return fiber;
    }

    public double getSugars() {
        return sugars;
    }

    public double getProtein() {
        return protein;
    }

    public double getSodium() {
        return sodium;
    }

    public double getCalcium() {
        return calcium;
    }

    public double getMagnesium() {
        return magnesium;
    }

    public double getPotassium() {
        return potassium;
    }

    public double getIron() {
        return iron;
    }

    public double getZinc() {
        return zinc;
    }

    public double getPhosphorous() {
        return phosphorous;
    }

    public double getVitamina() {
        return vitamina;
    }

    public double getVitaminc() {
        return vitaminc;
    }

    public double getB1() {
        return b1;
    }

    public double getB2() {
        return b2;
    }

    public double getB3() {
        return b3;
    }

    public double getB6() {
        return b6;
    }

    public double getFolate() {
        return folate;
    }

    public double getVitamine() {
        return vitamine;
    }

    public double getVitamink() {
        return vitamink;
    }

    public double getWeight() {
        return weight;
    }
}
