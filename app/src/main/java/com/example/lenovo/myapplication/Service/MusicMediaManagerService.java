package com.example.lenovo.myapplication.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.lenovo.myapplication.IMyMusicMananger;
import com.example.lenovo.myapplication.MusicInfo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MusicMediaManagerService extends Service {

    private MediaPlayer mMediaPlayer;
    private int index;
    private boolean isFirst;
    private CopyOnWriteArrayList<MusicInfo> mMusicInfos = new CopyOnWriteArrayList<MusicInfo>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("test", "onStart");
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i("test", "onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("test", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("test", "Destroy");
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i("test", "onRebind");
        super.onRebind(intent);
    }

    private Binder mBinder = new IMyMusicMananger.Stub(){

        @Override
        public void play(int curIndex) throws RemoteException {
            if(mMediaPlayer != null){
                if(isFirst){ // 是第一次
                    try {
                        if(curIndex >= 0){
                            index = curIndex;
                        }
                        mMediaPlayer.setDataSource(mMusicInfos.get(index).getDataPath());
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isFirst = false;
                }else { // 不是第一次
                    if (curIndex >= 0 && index != curIndex) { // 需要换歌
                        try {
                            index = curIndex;
                            mMediaPlayer.reset();
                            mMediaPlayer.setDataSource(mMusicInfos.get(index).getDataPath());
                            mMediaPlayer.prepare();
                            mMediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.pause();
                        } else {
                            mMediaPlayer.start();
                        }
                    }
                }
            }
        }

        @Override
        public void pause() throws RemoteException {

        }

        @Override
        public void playNext() throws RemoteException {
            if(mMediaPlayer != null){
                mMediaPlayer.reset();
                try {
                    mMediaPlayer.setDataSource(mMusicInfos.get(++index).getDataPath());
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void palyPre() throws RemoteException {
            if(mMediaPlayer != null){
                mMediaPlayer.reset();
                if(index - 1 >= 0){
                    try {
                        mMediaPlayer.setDataSource(mMusicInfos.get(--index).getDataPath());
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void currentProgress() throws RemoteException {
            mMediaPlayer.getCurrentPosition();
        }

        @Override
        public void addMusic(MusicInfo music) throws RemoteException {
            mMusicInfos.add(music);
        }

        @Override
        public List<MusicInfo> getMusicList() throws RemoteException {
            return mMusicInfos;
        }

        @Override
        public void delteAllMusic() throws RemoteException {
            mMusicInfos.clear();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("test", "onCreate");
        if(mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        index = 0;
        isFirst = true;
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(mMusicInfos.get(++index).getDataPath());
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mMediaPlayer.release();
                return false;
            }
        });
    }

}
