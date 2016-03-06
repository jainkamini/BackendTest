package com.example.android.mytestbackennew;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamini.myapplication.backend.quoteApi.QuoteApi;
import com.example.kamini.myapplication.backend.quoteApi.model.Quote;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class MainActivity extends Activity {
    public final static String APP_PATH_SD_CARD = "/DesiredSubfolderName/";
    public final static String APP_THUMBNAIL_PATH_SD_CARD = "thumbnails";
    public  static String filenamesave =null;

    private String LOG_TAG=MainActivity.class.getSimpleName();

    TextView tv;
    ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=  (TextView)findViewById(R.id.txthello);
        Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ytlogo);

  boolean filesavestatus=     saveImageToExternalStorage(img);
     //  boolean filesavestatus=  getAlbumStorageDir(getApplicationContext(),img);

        String what="gooooo";
        String who="ram";
    if (   filesavestatus=true) {
        who=filenamesave;
    }
                String[]args={what,who};
        img1=(ImageView)findViewById(R.id.imagefile);

       // new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Manfredhjghgjhgjhgjhgjhgjhgjhgjgj"));
        new EndpointsAsyncTask().execute(args);

       // new EndpointsAsyncTask().execute(argc));
    }


   // * Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    // file save in public dir save
    public File getAlbumStorageDir1(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }
    // file save in private dir save
    public Boolean getAlbumStorageDir(Context context, Bitmap image) {
        // Get the directory for the app's private pictures directory.
        if (isExternalStorageWritable()==false)
        {
            return false;
        }
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), "youtube.png");
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
            Log.e(LOG_TAG, "file path:"+file);
            //file path:/storage/emulated/0/Android/data/com.example.android.mytestbackennew/files/Pictures/test.png
        }
        try {

            OutputStream fOut = null;


            file.createNewFile();
            fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            filenamesave = file.getAbsolutePath();
            return true;

        } catch (Exception e) {
            Log.e("saveToExternalStorage()", e.getMessage());
            return false;

            //  return file;
        }
    }

    public boolean saveImageToExternalStorage(Bitmap image)
        {
      //  String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
if (isExternalStorageWritable()==false)
{
    return false;
}
           String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        try {
            File dir = new File(fullPath);


            if (!dir.exists()) {
               if (! dir.mkdirs()) {
                   Log.e(LOG_TAG, "Directory not created");
                   Log.e(LOG_TAG, "file path:" + dir);
               }
            }


            OutputStream fOut = null;
            File file = new File(fullPath, "youtube.png");
            file.createNewFile();
            fOut = new FileOutputStream(file);

// 100 means no compression, the lower you go, the stronger the compression
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            filenamesave=file.getAbsolutePath();
            return true;

        } catch (Exception e) {
            Log.e("saveToExternalStorage()", e.getMessage());
            return false;
        }
    }



    public Bitmap getThumbnail(String filename) {

       // String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
       String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() ;
        /*File file = new File(getApplicationContext().getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), "youtube.png");*/
       // String fullPath=file.getPath();
        Bitmap thumbnail = null;

// Look for the file on the external storage
        try {
            if (isSdReadable() == true) {
              //  thumbnail = BitmapFactory.decodeFile(fullPath + "/" + filename);
                thumbnail = BitmapFactory.decodeFile(fullPath+"/" +  "youtube.png");
                img1.setImageBitmap(thumbnail);

            }
        } catch (Exception e) {
            Log.e("getThumbnail() on external storage", e.getMessage());
        }

// If no file on external storage, look in internal storage
        if (thumbnail == null) {
            try {
                File filePath = getApplicationContext().getFileStreamPath(filename);
                FileInputStream fi = new FileInputStream(filePath);
                thumbnail = BitmapFactory.decodeStream(fi);
            } catch (Exception ex) {
                Log.e("getThumbnail() on internal storage", ex.getMessage());
            }
        }
        return thumbnail;
    }
    public boolean isSdReadable() {

        boolean mExternalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
// We can read and write the media
            mExternalStorageAvailable = true;
            Log.i("isSdReadable", "External storage card is readable.");
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
// We can only read the media
            Log.i("isSdReadable", "External storage card is readable.");
            mExternalStorageAvailable = true;
        } else {
// Something else is wrong. It may be one of many other
// states, but all we need to know is we can neither read nor write
            mExternalStorageAvailable = false;
        }

        return mExternalStorageAvailable;
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
                        .setRootUrl("https://celtic-maxim-123918.appspot.com/_ah/api/")
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
                return quote1.getWho();
            } catch (IOException e) {
                Log.e(LOG_TAG,e.getMessage());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
           // Toast.makeText(result, Toast.LENGTH_LONG).show();
           // tv.setText(result);
            getThumbnail(result);
           //g img1.setImageURI();
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
