package SplashAwards2k18;

import org.json.JSONObject;

public class Calories {
    private float calories;
    private float fat;
    private float saturatedfat;
    private float monosaturatedfat;
    private float polysaturatedfat;
    private float carbs;
    private float fiber;
    private float sugars;
    private float protein;
    private float sodium;
    private float calcium;
    private float magnesium;
    private float potassium;
    private float iron;
    private float zinc;
    private float phosphorous;
    private float vitamina;
    private float vitaminc;
    private float b1;
    private float b2;
    private float b3;
    private float b6;
    private float folate;
    private float vitamine;
    private float vitamink;

    public static float Scalories=2000;
    public static float Sfat=70;
    public static float Ssaturatedfat=24;
    public static float Smonosaturatedfat;
    public static float Spolysaturatedfat;
    public static float Scarbs=300;
    public static float Sfiber=25;
    public static float Ssugars=90;
    public static float Sprotein=50;
    public static float Ssodium=2300;
    public static float Scalcium=995;
    public static float Smagnesium=400;
    public static float Spotassium=3500;
    public static float Siron=18;
    public static float Szinc=15;
    public static float Sphosphorous=700;
    public static float Svitamina=900;
    public static float Svitaminc=60;
    public static float Sb1=(float)1.5;
    public static float Sb2=(float)1.7;
    public static float Sb3=20;
    public static float Sb6=2;
    public static float Sfolate=400;
    public static float Svitamine=400;
    public static float Svitamink=80;

    private float weight;

    public Calories(JSONObject json){
        //continue....
    }

    public float getCalories() {
        return calories;
    }

    public float getFat() {
        return fat;
    }

    public float getSaturatedfat() {
        return saturatedfat;
    }

    public float getMonosaturatedfat() {
        return monosaturatedfat;
    }

    public float getPolysaturatedfat() {
        return polysaturatedfat;
    }

    public float getCarbs() {
        return carbs;
    }

    public float getFiber() {
        return fiber;
    }

    public float getSugars() {
        return sugars;
    }

    public float getProtein() {
        return protein;
    }

    public float getSodium() {
        return sodium;
    }

    public float getCalcium() {
        return calcium;
    }

    public float getMagnesium() {
        return magnesium;
    }

    public float getPotassium() {
        return potassium;
    }

    public float getIron() {
        return iron;
    }

    public float getZinc() {
        return zinc;
    }

    public float getPhosphorous() {
        return phosphorous;
    }

    public float getVitamina() {
        return vitamina;
    }

    public float getVitaminc() {
        return vitaminc;
    }

    public float getB1() {
        return b1;
    }

    public float getB2() {
        return b2;
    }

    public float getB3() {
        return b3;
    }

    public float getB6() {
        return b6;
    }

    public float getFolate() {
        return folate;
    }

    public float getVitamine() {
        return vitamine;
    }

    public float getVitamink() {
        return vitamink;
    }

    public float getWeight() {
        return weight;
    }
}
