//
// Created by Lenovo on 2015/12/7.
//
#include "com_example_lenovo_myapplication_MainActivity2.h"
JNIEXPORT jstring JNICALL Java_com_example_lenovo_myapplication_MainActivity2_getStringFromNative
        (JNIEnv * env, jobject obj){
    return (*env)->NewStringUTF(env,"I'm comes from to Native Function!");
}