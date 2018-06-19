package SplashAwards2k18;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.DecimalFormat;

@Parcel
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
    public DecimalFormat formatter = new DecimalFormat("#0.00");

    private double weight=0;
    public Calories() {};
    public Calories(double cals){
        calories=cals;
    }
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
                protein=nutrients.getJSONObject("PROCNT").getDouble("quantity");
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
        return Double.parseDouble(formatter.format(transfat));
    }

    public double getCholestrol() {
        return Double.parseDouble(formatter.format(cholestrol));
    }

    public double getB12() {
        return Double.parseDouble(formatter.format(b12));
    }

    public double getVitamind() {
        return Double.parseDouble(formatter.format(vitamind));
    }

    public double getCalories() {
        return Double.parseDouble(formatter.format(calories));
    }

    public double getFat() {
        return Double.parseDouble(formatter.format(fat));
    }

    public double getSaturatedfat() {
        return Double.parseDouble(formatter.format(saturatedfat));
    }

    public double getMonosaturatedfat() {
        return Double.parseDouble(formatter.format(monosaturatedfat));
    }

    public double getPolysaturatedfat() {
        return Double.parseDouble(formatter.format(polysaturatedfat));
    }

    public double getCarbs() {
        return Double.parseDouble(formatter.format(carbs));
    }

    public double getFiber() {
        return Double.parseDouble(formatter.format(fiber));
    }

    public double getSugars() {
        return Double.parseDouble(formatter.format(sugars));
    }

    public double getProtein() {
        return Double.parseDouble(formatter.format(protein));
    }

    public double getSodium() {
        return Double.parseDouble(formatter.format(sodium));
    }

    public double getCalcium() {
        return Double.parseDouble(formatter.format(calcium));
    }

    public double getMagnesium() {
        return Double.parseDouble(formatter.format(magnesium));
    }

    public double getPotassium() {
        return Double.parseDouble(formatter.format(potassium));
    }

    public double getIron() {
        return Double.parseDouble(formatter.format(iron));
    }

    public double getZinc() {
        return Double.parseDouble(formatter.format(zinc));
    }

    public double getPhosphorous() {
        return Double.parseDouble(formatter.format(phosphorous));
    }

    public double getVitamina() {
        return Double.parseDouble(formatter.format(vitamina));
    }

    public double getVitaminc() {
        return Double.parseDouble(formatter.format(vitaminc));
    }

    public double getB1() {
        return Double.parseDouble(formatter.format(b1));
    }

    public double getB2() {
        return Double.parseDouble(formatter.format(b2));
    }

    public double getB3() {
        return Double.parseDouble(formatter.format(b3));
    }

    public double getB6() {
        return Double.parseDouble(formatter.format(b6));
    }

    public double getFolate() {
        return Double.parseDouble(formatter.format(folate));
    }

    public double getVitamine() {
        return Double.parseDouble(formatter.format(vitamine));
    }

    public double getVitamink() {
        return Double.parseDouble(formatter.format(vitamink));
    }

    public double getWeight() {
        return Double.parseDouble(formatter.format(weight));
    }

    @Override
    public String toString(){
        return  "Nutritional information:\n"+
                "Calories       :"+getCalories()+"kcal" +"\n"+
                "Fat            :"+getFat()+"g" +"\n"+
                "   saturated   :"+getSaturatedfat()+"g" +"\n"+
                "   monounsaturated:"+getMonosaturatedfat()+"g" +"\n"+
                "   polyunsaturated:"+getPolysaturatedfat()+"g" +"\n"+
                "   trans       :"+getTransfat()+"g" +"\n"+
                "Carbohydrates  :"+getCarbs()+"g" +"\n"+
                "Fiber          :"+getFiber()+"g" +"\n"+
                "Sugars         :"+getSugars()+"g" +"\n"+
                "Protein        :"+getProtein()+"g" +"\n"+
                "Cholestrol     :"+getCholestrol()+"mg"+"\n"+
                "Sodium         :"+getSodium()+"mg"+"\n"+
                "Calcium        :"+getCalcium()+"mg" +"\n"+
                "Magnesium      :"+getMagnesium()+"mg" +"\n"+
                "Potassium      :"+getPotassium()+"mg" +"\n"+
                "Iron           :"+getIron()+"mg" +"\n"+
                "Zinc           :"+getZinc()+"mg" +"\n"+
                "Phosphorus     :"+getPhosphorous()+"mg" +"\n"+
                "Vitamin A      :"+getVitamina()+"µg" +"\n"+
                "Vitamin C      :"+getVitaminc()+"mg" +"\n"+
                "Thiamin (B1)   :"+getB1()+"mg" +"\n"+
                "Riboflavin (B2):"+getB2()+"mg" +"\n"+
                "Niacin (B3)    :"+getB3()+"mg" +"\n"+
                "Vitamin B6     :"+getB6()+"mg" +"\n"+
                "Folate (total) :"+getFolate()+"µg" +"\n"+
                "Vitamin B12    :"+getB12()+"µg" +"\n"+
                "Vitamin D      :"+getVitamind()+"µg" +"\n"+
                "Vitamin E      :"+getVitamine()+"mg" +"\n"+
                "Vitamin K      :"+getVitamink()+"µg" ;

    }

}
