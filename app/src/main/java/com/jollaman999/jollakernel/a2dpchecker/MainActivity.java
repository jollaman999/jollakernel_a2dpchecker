package com.jollaman999.jollakernel.a2dpchecker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        try {
            Runtime.getRuntime().exec("su");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
