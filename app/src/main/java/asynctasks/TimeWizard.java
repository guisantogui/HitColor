package asynctasks;

import android.content.Context;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeWizard {


    TimerAsyncTask timerAsyncTask;

    public TimeWizard(TextView textView, Context ctx, int countSum){
        timerAsyncTask  = new TimerAsyncTask(textView, ctx, countSum);
    }



    public void doIt(){
        final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

        final Runnable task =
        new Runnable() {

            @Override
            public void run() {
                timerAsyncTask.execute();

                if(true) {
                    exec.shutdown();    // shutdown this execution
                    //exec = Executors.newSingleThreadScheduledExecutor();
                    exec.scheduleAtFixedRate(task, 0, 100, TimeUnit.MILLISECONDS);
                }
            }
        };

        exec.scheduleAtFixedRate(task, 0, 100, TimeUnit.MILLISECONDS);
    }

}