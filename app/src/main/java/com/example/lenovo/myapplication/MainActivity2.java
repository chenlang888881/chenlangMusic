package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends Activity implements View.OnClickListener{

    static {
        System.loadLibrary("JniTest");
    }

    public native String getStringFromNative();
    TextView startButton;
    TextView endButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        startButton = (TextView) findViewById(R.id.startButton);
        startButton.setText(getStringFromNative());
        startButton.setOnClickListener(this);
        endButton = (TextView) findViewById(R.id.endButton);
        endButton.setOnClickListener(this);
        ExpandableListView expandableListView = new ExpandableListView(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) endButton.getLayoutParams();
        layoutParams.width = startButton.getWidth();
        layoutParams.height = startButton.getHeight();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Scroller scroller = new Scroller(this);
        getWindow().getDecorView();
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.endButton){
            endButton.setVisibility(View.GONE);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("wdscx");
            arrayList.add("dds");
            intent.putStringArrayListExtra("users", arrayList);
            startActivity(intent);
        }
    }
}
