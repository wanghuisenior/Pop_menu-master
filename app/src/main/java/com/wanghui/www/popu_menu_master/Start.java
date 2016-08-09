package com.wanghui.www.popu_menu_master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wanghui.www.popu_menu_master.activity.MainActivity;
import com.wanghui.www.popu_menu_master.activity.RecyclerView;

public class Start extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViewById(R.id.btn_pop).setOnClickListener(this);
        findViewById(R.id.btn_recyclerview).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pop:
                startActivity(new Intent(Start.this,MainActivity.class));
                break;
            case R.id.btn_recyclerview:
                startActivity(new Intent(Start.this,RecyclerView.class));
                break;
        }
    }
}
