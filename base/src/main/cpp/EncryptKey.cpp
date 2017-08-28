//
// Created by potoyang on 2017/8/7.
//
#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_chh_yinbao_utils_EncrypJNIUtils_getKey(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "11111111111111111111111111111111";
    return env->NewStringUTF(hello.c_str());
}

