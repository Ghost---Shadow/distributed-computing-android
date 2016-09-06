package org.cse323.distributedcomputingandroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.distributed.client.Client;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText etIp;
    private EditText etPort;

    private Button  bConnect;

    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etIp = (EditText)findViewById(R.id.etIp);
        etPort = (EditText)findViewById(R.id.etPort);
        bConnect = (Button)findViewById(R.id.bConnect);
        tvLog = (TextView)findViewById(R.id.tvLog);

        bConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NetworkTask().execute(etIp.getText()+"",etPort.getText()+"");
            }
        });
    }
    private class NetworkTask extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... data) {
            Client client = Client.getInstance();
            client.connect(data[0],Integer.parseInt(data[1]));
            return (long)0;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {

        }
    }
}
