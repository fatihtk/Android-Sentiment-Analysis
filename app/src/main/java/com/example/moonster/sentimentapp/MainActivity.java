package com.example.moonster.sentimentapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;
    String sentiment;


    private class AskWatsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... textsToAnalyse) {
            System.out.println(editText.getText());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("what is happening inside a thread - we are running Watson AlchemyAPI");
                }
            });
            AlchemyLanguage service = new AlchemyLanguage();
            service.setApiKey("844de29ef7761fd309c079582e53bc304c10829f");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AlchemyLanguage.TEXT, editText.getText());
            DocumentSentiment sentiment = service.getSentiment(params).execute();


            System.out.println(sentiment);

            return sentiment.getSentiment().getType().name();
        }

        @Override
        protected void onPostExecute(String result){
            textView.setText("The message;s sentiment is: " + result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize UI parameters
        textView = (TextView)  findViewById(R.id.textView);
        editText = (EditText)  findViewById(R.id.editText);
        button = (Button)  findViewById(R.id.button);

        //fire action when button is pressed
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Logging to the console that the button pressed for the text : " + editText.getText());
                textView.setText("Displaying at UI the sentiment to be checked for : " + editText.getText());

                AskWatsonTask task = new AskWatsonTask();
                task.execute(new String[]{});
            }
        });
    }
}
