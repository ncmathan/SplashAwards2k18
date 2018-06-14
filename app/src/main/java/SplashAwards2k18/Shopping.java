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

import SplashAwards2k18.R;

public class Shopping extends AppCompatActivity {
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

        fairprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("https://www.fairprice.com.sg");
                fairprice.setVisibility(View.INVISIBLE);
                coldstorage.setVisibility(View.INVISIBLE);
                redmart.setVisibility(View.INVISIBLE);
                isWebView=true;
            }
        });
        coldstorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("https://coldstorage.com.sg");
                fairprice.setVisibility(View.INVISIBLE);
                coldstorage.setVisibility(View.INVISIBLE);
                redmart.setVisibility(View.INVISIBLE);
                isWebView=true;
            }
        });
        redmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("https://redmart.com");
                fairprice.setVisibility(View.INVISIBLE);
                coldstorage.setVisibility(View.INVISIBLE);
                redmart.setVisibility(View.INVISIBLE);
                isWebView=true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fairprice.setVisibility(View.INVISIBLE);
                coldstorage.setVisibility(View.INVISIBLE);
                redmart.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.INVISIBLE);
                shoppinglist.setVisibility(View.VISIBLE);

                if(isWebView){
                webView.setVisibility(View.INVISIBLE);}

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

            }
        });
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