package hu.petrik.karacsonyiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView countdown;
    private Timer timer;
    private Date karacsony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        countdown = findViewById(R.id.countdown);
        Calendar most = Calendar.getInstance();
        int ev = most.get(Calendar.YEAR);
        int honap = most.get(Calendar.MONTH);
        int nap = most.get(Calendar.DATE);
        if (honap == 11 && nap > 24) {
            ev++;
        }
        Calendar karacsony = Calendar.getInstance();
        karacsony.set(ev, 11, 25,0,0,0);
        this.karacsony = karacsony.getTime();
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date most = Calendar.getInstance().getTime();
                long hatralevoIdo = karacsony.getTime() - most.getTime();

                long masodpercMili = 1000;
                long percmili = masodpercMili * 60;
                long oramili = percmili * 60;
                long napmili = oramili * 24;

                long nap = hatralevoIdo / napmili;
                hatralevoIdo%=napmili;
                long ora=hatralevoIdo/ oramili;
                hatralevoIdo %= oramili;
                long perc=hatralevoIdo/percmili;
                hatralevoIdo%=percmili;
                long masodperc=hatralevoIdo/masodpercMili;

                String hatralevoSzoveg=String.format("%d nap %02d:%02d:%02d:%02d",nap,ora,perc,masodperc);

                runOnUiThread(()->{
                    countdown.setText("");
                });

           }
        };        timer.schedule(task, 0, 500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}