package com.example.sec.countdowntimer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private CountTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.count);
    }

    public void start(View view) {
        mTask = new CountTask();
        mTask.execute(0);
    }

    public void clear(View view) {
        mTask.cancel(true);
        mTextView.setText("0");
    }

    public class CountTask extends AsyncTask<Integer, Integer, Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {
            do{
                try {
                    Thread.sleep(1000);
                    params[0]++;
                    publishProgress(params[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(params[0] < 10);
            return params[0];
        }

        @Override
        protected  void onProgressUpdate(Integer ... values){
            mTextView.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(Integer result){
            Toast.makeText(MainActivity.this,""+result,Toast.LENGTH_SHORT).show();
        }
    }
}
