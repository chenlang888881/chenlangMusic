package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Handler;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.lenovo.myapplication.Service.MusicMediaManagerService;
import com.example.lenovo.myapplication.adapter.MusicInfoAdapter;
import com.example.lenovo.myapplication.util.ChineseToFirstCapital;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener{

    float mLastX;
    float mLastY;
    TextView textView;
    Handler handler;
    private IMyMusicMananger musicMananger;
    private Button preButton;
    private Button playButton;
    private Button nextButton;
    private Button stopButton;
    private TextView musicName;
    private ExpandableListView expandableListView;
    List<List<MusicInfo>> child;
    List<String> grounp;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicMananger = IMyMusicMananger.Stub.asInterface(service);

            setMusicInfo(musicMananger);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void setMusicInfo(final IMyMusicMananger musicMananger) {
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        try {
            musicMananger.delteAllMusic();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        while (cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            title += " " + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            title += " " + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            title += " " + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
            cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
            musicInfo.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
            musicInfo.setDataPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
            musicInfo.setDisplayName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
            musicInfo.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
            musicInfo.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
            try {
                musicMananger.addMusic(musicInfo);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d("test", title);
        }


        try {
            final List<MusicInfo> musicInfos = new ArrayList<MusicInfo>();
            musicInfos.addAll(musicMananger.getMusicList());
            for (MusicInfo musicInfo : musicInfos){
                musicInfo.setSortDisplayName(ChineseToFirstCapital.getSpell(musicInfo.getDisplayName()));
            }
            // 排序
            Collections.sort(musicInfos, new Comparator<MusicInfo>() {
                @Override
                public int compare(MusicInfo lhs, MusicInfo rhs) {
                    return lhs.getSortDisplayName().toUpperCase().compareTo(rhs.getSortDisplayName().toUpperCase());
                }
            });
            musicMananger.delteAllMusic();
            int lenght = musicInfos.size();
            for (int i = 0 ; i < lenght ; i++){
                musicInfos.get(i).setIndex(i);
                musicMananger.addMusic(musicInfos.get(i));
            }

            // 放入集合
            Map<String,Integer> map = new HashMap<String,Integer>();
            grounp = new ArrayList<String>();
            child = new ArrayList<List<MusicInfo>>();
            int count = 0;
            for (MusicInfo musicInfo : musicInfos){
                Log.d("test", "music " + musicInfo.getDisplayName() + ": " + musicInfo.getSortDisplayName());
                String string  = new Character(musicInfo.getSortDisplayName().charAt(0)).toString();
                string = string.toUpperCase();
                if(map.get(string) == null){
                    List<MusicInfo> list = new ArrayList<MusicInfo>();
                    list.add(musicInfo);
                    child.add(list);
                    grounp.add(string);
                    map.put(string, count++);
                }else {
                    child.get(count-1).add(musicInfo);
                }
            }
            Log.d("test", "count "+count);
            for(int i =0 ;i<count;i++ ){
                Log.d("test", "count "+child.get(i).size());
            }
            MusicInfoAdapter adapter = new MusicInfoAdapter(grounp, child, this);
            expandableListView.setAdapter(adapter);
            for(int i= 0; i < count; i++){
                expandableListView.expandGroup(i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        handler = new Handler();

        expandableListView = (ExpandableListView) findViewById(R.id.expandListView);
         intent = new Intent(this, MusicMediaManagerService.class);
        startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        preButton = (Button) findViewById(R.id.preButton);
        playButton = (Button) findViewById(R.id.playButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        playButton.setOnClickListener(this);
        preButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        expandableListView.setGroupIndicator(null);

        expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("test", "item");
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                try {
                    musicMananger.play(child.get(groupPosition).get(childPosition).getIndex());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.preButton:
                try {
                    musicMananger.palyPre();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.playButton:
                try {
                    musicMananger.play(-1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.nextButton:
                try {
                    musicMananger.playNext();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.stopButton:
                unbindService(mConnection);
                stopService(intent);
        }
    }

}
