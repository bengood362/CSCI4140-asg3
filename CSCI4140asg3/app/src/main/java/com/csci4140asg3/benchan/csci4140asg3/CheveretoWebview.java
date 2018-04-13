package com.csci4140asg3.benchan.csci4140asg3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

public class CheveretoWebview extends AppCompatActivity {

    private String targetUrl = "http://10.0.2.2:8080";
    WebView webview;
    Bundle intent_extras;
    int login_try=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chevereto_webview);

        intent_extras = getIntent().getExtras();

        webview = findViewById(R.id.chevereto_webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setMinimumFontSize(1);
        webSettings.setMinimumLogicalFontSize(1);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        setContentView(webview);
        WebViewClient webviewClient = new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(login_try<3){
                    login_try++;
                    String username = intent_extras.getString("username");
                    String password = intent_extras.getString("password");
                    CheveretoWebview.this.login(username, password);
                }
            }
        };
        webview.setWebViewClient(webviewClient);
        webview.loadUrl(targetUrl);
        String username = intent_extras.getString("username");
        String password = intent_extras.getString("password");
        login(username, password);
    }

    protected void alert(String title, String message){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    protected void login(String username, String password){
        String form_action = "login";
        String js = "    (function(){ return 1})();"+
            "    var form = document.createElement(\"form\");"+
                "   var auth_token = document.getElementsByName(\"auth_token\")[0].value"+
                "    var element1 = document.createElement(\"input\"); "+
                "    var element2 = document.createElement(\"input\");  "+
                "    var element3 = document.createElement(\"input\");  "+
                "    var element4 = document.createElement(\"input\");  "+
                "    form.method = \"POST\";"+
                "    form.action = \""+(form_action)+"\";   "+
                "    element1.value=\""+username+"\";"+
                "    element1.name=\"login-subject\";"+
                "    form.appendChild(element1);  "+
                "    element2.value=\""+password+"\";"+
                "    element2.name=\"password\";"+
                "    form.appendChild(element2);"+
                "    element3.value=auth_token;"+
                "    element3.name=\"auth_token\";"+
                "    form.appendChild(element3);"+
                "    element4.value=\""+username+"\";"+
                "    element4.name=\"email\";"+
                "    form.appendChild(element4);  "+
                "    document.body.appendChild(form);"+
                "    form.submit();"+
                "    (function(){ return 1})();";
        webview.evaluateJavascript(js, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
//                alert("onReceiveValue", value);
            }
        });
    }
    @Override
    protected void onResume()
    {
        super.onResume();
    }
}
