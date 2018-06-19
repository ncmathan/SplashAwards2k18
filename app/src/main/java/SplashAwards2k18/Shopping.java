package SplashAwards2k18;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.support.design.widget.FloatingActionButton;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import SplashAwards2k18.R;

public class Shopping extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();

    private Button fairprice;
    private Button coldstorage;
    private Button redmart;
    private FloatingActionButton fab;
    private WebView webView;
    private boolean isWebView;

    private RelativeLayout shoppinglist;
    private Button closelist;
    private Button clearlist;
    private EditText inputitem;
    private Button createitem;
    private LinearLayout list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        isWebView=false;
        fairprice=(Button)findViewById(R.id.fairprice);
        coldstorage=(Button)findViewById(R.id.coldstorage);
        redmart=(Button)findViewById(R.id.redmart);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        webView=(WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewController());
        shoppinglist=(RelativeLayout)findViewById(R.id.listshopping);
        closelist=(Button)findViewById(R.id.closelist);
        clearlist=(Button)findViewById(R.id.clearlist);
        inputitem=(EditText)findViewById(R.id.iteminput);
        createitem=(Button)findViewById(R.id.createitem);
        list=(LinearLayout)findViewById(R.id.list);
        int num=getIntent().getIntExtra("num",0);
        open(num);
        fairprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(1);
            }
        });
        coldstorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(2);
            }
        });
        redmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(3);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(4);
            }
        });
        closelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWebView){
                    shoppinglist.setVisibility(View.INVISIBLE);
                    webView.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                }
                else{
                    fairprice.setVisibility(View.VISIBLE);
                    coldstorage.setVisibility(View.VISIBLE);
                    redmart.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    shoppinglist.setVisibility(View.INVISIBLE);
                }
            }
        });
        clearlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.removeAllViews();
                dbRef.child("shoppinglist").removeValue();
            }
        });
        createitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox box=new CheckBox(getApplicationContext());
                box.setText(inputitem.getText().toString());
                box.setTextSize(25);
                box.setChecked(false);
                list.addView(box);
                dbRef.child("shoppinglist").push().setValue(inputitem.getText().toString());

            }
        });
    }



    public void open(int num){
        fairprice.setVisibility(View.INVISIBLE);
        coldstorage.setVisibility(View.INVISIBLE);
        redmart.setVisibility(View.INVISIBLE);
        if(num==0){
            fairprice.setVisibility(View.VISIBLE);
            coldstorage.setVisibility(View.VISIBLE);
            redmart.setVisibility(View.VISIBLE);
        }
        if(num==1){//Fairprice
            webView.loadUrl("https://www.fairprice.com.sg");
            webView.setVisibility(View.VISIBLE);
            isWebView=true;
        }
        else if(num==2){//ColdStorage
            webView.loadUrl("https://coldstorage.com.sg");
            webView.setVisibility(View.VISIBLE);
            isWebView=true;
        }
        else if(num==3){
            webView.loadUrl("https://redmart.com");
            webView.setVisibility(View.VISIBLE);
            isWebView=true;
        }
        else if(num==4){
            fab.setVisibility(View.INVISIBLE);
            shoppinglist.setVisibility(View.VISIBLE);

            if(isWebView){
                webView.setVisibility(View.INVISIBLE);}
            list.removeAllViews();
            dbRef.child("shoppinglist").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        CheckBox box=new CheckBox(getApplicationContext());
                        box.setText(child.getValue().toString());
                        box.setTextSize(25);
                        box.setChecked(false);
                        list.addView(box);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Do nothing
                }
            });
        }
    }




    @Override
    public void onBackPressed() {
        if(webView.getVisibility()==View.VISIBLE){
            if(webView.canGoBack()){
                webView.goBack();
            }
            else{
            webView.setVisibility(View.INVISIBLE);
            fairprice.setVisibility(View.VISIBLE);
            coldstorage.setVisibility(View.VISIBLE);
            redmart.setVisibility(View.VISIBLE);}
        }
        else if(shoppinglist.getVisibility()==View.VISIBLE ){
            if(isWebView){
                shoppinglist.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            else{
                fairprice.setVisibility(View.VISIBLE);
                coldstorage.setVisibility(View.VISIBLE);
                redmart.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                shoppinglist.setVisibility(View.INVISIBLE);
            }
        }
        else{
        super.onBackPressed();}

    }
}

 class WebViewController extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

}