// IMyMusicMananger.aidl
package com.example.lenovo.myapplication;
import com.example.lenovo.myapplication.MusicInfo;
// Declare any non-default types here with import statements

interface IMyMusicMananger {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void play(int index);
    void pause();
    void playNext();
    void palyPre();
    void currentProgress();
    void addMusic(in MusicInfo music);
    List<MusicInfo> getMusicList();
    void delteAllMusic();
}
