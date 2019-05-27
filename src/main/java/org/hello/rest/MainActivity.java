package org.hello.rest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    final String textSource = "https://gturnquist-quoters.cfapps.io/api/random";
    Button btnCallRestApi;
    TextView textMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textMsg = findViewById(R.id.textView);
        btnCallRestApi = findViewById(R.id.btnCallRestApi);
        btnCallRestApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call Rest Api

               new HttpReqTask().execute();

            }
        });
    }


    private class HttpReqTask extends AsyncTask<Void, Void, Void> {

        String textResult;
        @Override
        protected Void doInBackground(Void... params){
            URL textURL;
            try{
                textURL = new URL(textSource);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(textURL.openStream()));
                String stringBuffer;
                String stringText = "";
                while((stringBuffer = bufferedReader.readLine())!=null){
                    stringText+=stringBuffer;
                }
                bufferedReader.close();
                textResult=stringText;

            }
            catch (MalformedURLException e){
                e.printStackTrace();
                textResult=e.toString();
            }
            catch (IOException e){
                e.printStackTrace();
                textResult=e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            textMsg.setText(textResult);
            super.onPostExecute(result);
        }

    }
}
