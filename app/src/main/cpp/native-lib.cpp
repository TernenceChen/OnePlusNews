//
// Created by oneplus on 19-10-23.
//

#include <jni.h>
#include <string>
#include "native-lib.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_oneplus_opnew_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject) {
    std::string hello = "This is a C++ message";
    return env->NewStringUTF(hello.c_str());
}