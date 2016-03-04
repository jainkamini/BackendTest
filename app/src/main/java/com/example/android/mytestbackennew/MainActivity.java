package com.example.android.mytestbackennew;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamini.myapplication.backend.quoteApi.QuoteApi;
import com.example.kamini.myapplication.backend.quoteApi.model.Quote;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;



public class MainActivity extends Activity {


    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=  (TextView)findViewById(R.id.txthello);
        String what="Heloo";
        String who="me";
                String[]args={what,who};
       // new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Manfredhjghgjhgjhgjhgjhgjhgjhgjgj"));
        new EndpointsAsyncTask().execute(args);

       // new EndpointsAsyncTask().execute(argc));
    }

    class EndpointsAsyncTask extends AsyncTask<String,String,String> {

        private QuoteApi quoteApiService = null;
        private Context context;

        @Override
        protected String doInBackground(String... args) {
            if(quoteApiService == null) {  // Only do this once
                QuoteApi.Builder builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                       // .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setRootUrl("http:/celtic-maxim-123918.appspot.com/_ah/api/explorer")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                quoteApiService = builder.build();
            }

           // context = params[0].first;
           // String name = params[0].second;

            try {
                Quote quote=new Quote().setWhat(args[0]);
                quote.setWho(args[1]);
                Quote quote1=   quoteApiService.insert(quote).execute();
                return quote1.getWhat();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
           // Toast.makeText(result, Toast.LENGTH_LONG).show();
            tv.setText(result);
        }
    }

   /* class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

        private QuoteApi quoteApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if(quoteApiService == null) {  // Only do this once
                QuoteApi.Builder builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                       // .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setRootUrl("http:/celtic-maxim-123918.appspot.com/_ah/api/explorer")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                quoteApiService = builder.build();
            }

            context = params[0].first;
            String name = params[0].second;

            try {
                Quote quote=new Quote();
                quote.setWhat("Good");
                quote.setWho("me11");
                return quoteApiService.insert(quote).execute().getWhat();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();

            tv.setText(result.toString());
        }
    }*/

}
