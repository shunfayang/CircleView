package com.yangshunfa.circleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.yangshunfa.circleview.moose.WaveView;

public class HomeActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnView();
//        bezierView();
//        waveAndArcView();
//        porterModeView();
//        constrainLayoutView();
    }

    private void btnView() {
        setContentView(R.layout.popup_view);
    }

    private void bezierView() {
        setContentView(R.layout.activity_home5);
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
