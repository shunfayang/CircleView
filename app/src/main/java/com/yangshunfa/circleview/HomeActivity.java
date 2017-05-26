package com.yangshunfa.circleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yangshunfa.circleview.moose.WaveView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        waveAndArcView();
//        porterModeView();
//        constrainLayoutView();
    }

    private void porterModeView() {
        setContentView(R.layout.activity_home4);
    }

    private void constrainLayoutView() {
        setContentView(R.layout.activity_home2);
    }

    private void waveAndArcView() {
        setContentView(R.layout.activity_home);
        WaveView waveView1 = (WaveView) findViewById(R.id.waveview1);
        WaveView waveView2 = (WaveView) findViewById(R.id.waveview2);
//        waveView1.setmColor("#a09298");
        waveView2.setmColor("#88a09298");
    }
}
