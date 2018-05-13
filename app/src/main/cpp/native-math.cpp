//
// Created by ToaHanDong on 2018/1/6.
//
#include <jni.h>
#include "../../../../myutils/src/main/cpp/add.h"
extern "C" {
jint Java_com_share_shareapplication_OpenCvActivity_addFromCpp(JNIEnv *env, jobject thiz, jint a, jint b){
    return add(a,b);
}
}



