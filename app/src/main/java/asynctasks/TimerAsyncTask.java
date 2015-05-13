package asynctasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.wordpress.amaz1ngc0de.animations.MainActivity;

public class TimerAsyncTask extends AsyncTask<Integer, String, String> {

    double actualTime;

    TextView tv;
    Context context;
    int total;

    public TimerAsyncTask(TextView textView, Context ctx, int countSum){
        tv = textView;
        actualTime = 0;
        context = ctx;
        total = countSum;
    }

    @Override
    protected String doInBackground(Integer... integers) {
        actualTime = integers[0] - actualTime;
        Log.d("GSS", "1 vez");
        while (actualTime > 0) {
            try {
                Thread.sleep(100L);
                actualTime -= 0.1D;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(String.valueOf(actualTime).substring(0,3)+"A A"+total);
        }

        return "Fechou o abacaxi";
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        tv.setText("Tempo restante: "+ values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        tv.setText(s);

        Intent i = new Intent(context, MainActivity.class);

        i.putExtra("CountSum", 1);

        context.startActivity(i);
    }
}
