package com.dw.hgfz.android;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.dw.hgfz.core.base.processor;

public class MainActivity extends Activity {

    private TextView textView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    /**
     * Called when the user clicks the Send button
     */
    public void sendMessage(View view) throws Exception {
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.edit_message);
        textView = (TextView) findViewById(R.id.display_message);
        String code = editText.getText().toString();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new hgfzTask().execute(code);
        } else {
            textView.setText("No network connection available.");
        }
    }

    private class hgfzTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return processor.process(urls[0], null, false);
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setText(result);
        }
    }
}
