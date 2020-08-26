//
// Created by PcCom on 04/06/2020.
//

#include <jni.h>
#include <string>
#include <vector>
#include "Exercise_List.hpp"

/**
 * Función para transformar un jstring a string.
 * @param env
 * @param jStr
 * @return
 */
std::string jstring2string(JNIEnv *env, jstring jStr) {
    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

    size_t length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte* pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    std::string ret = std::string((char *)pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}

//--------------------------------------------------------------

extern "C"
JNIEXPORT void JNICALL
Java_com_pjimeno97_exerciseeditortool_MainActivity_loadJson(JNIEnv *env, jobject thiz,
                                                            jstring javaPath) {
    std::string cpp_path = jstring2string(env, javaPath);

    Exercise_List::instance().load_json(cpp_path);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_pjimeno97_exerciseeditortool_MainActivity_printExercises(JNIEnv *env, jobject thiz) {
    std::string file_info = Exercise_List::instance().print_json();

    return env->NewStringUTF(file_info.c_str());
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_pjimeno97_exerciseeditortool_MainActivity_getCppExercises(JNIEnv *env, jobject thiz) {
    // Se carga la clase de java y su método constructor
    jclass exerciseJavaClass = env->FindClass("com/pjimeno97/exerciseeditortool/Exercise");
    jmethodID constructor = env->GetMethodID(exerciseJavaClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

    // Se carga la clase arraylist y sus métodos constructor y add
    jclass java_util_ArrayList = env->FindClass("java/util/ArrayList");
    jmethodID java_util_ArrayList_= env->GetMethodID(java_util_ArrayList, "<init>", "(I)V");
    jmethodID java_util_ArrayList_add = env->GetMethodID(java_util_ArrayList, "add", "(Ljava/lang/Object;)Z");

    // Se cargan los ejercicios actuales
    std::vector<Exercise> exercises = Exercise_List::instance().get_exercises();

    // Se crea un objeto arraylist del tamaño de exercises
    jobject result = env->NewObject(java_util_ArrayList, java_util_ArrayList_, (jint)exercises.size());

    // Se crean los objetos Exercise de java y se añaden a result
    for (auto &ex: exercises) {
        jobject J_Obj_Example = env->NewObject(exerciseJavaClass, constructor, env->NewStringUTF( ex.get_nombre().c_str()), env->NewStringUTF( ex.get_descripcion().c_str()), env->NewStringUTF( ex.get_url_video_tutorial().c_str()),env->NewStringUTF( ex.get_musculo().c_str()));
        env->CallBooleanMethod(result, java_util_ArrayList_add, J_Obj_Example);
        env->DeleteLocalRef(J_Obj_Example);
    }
    return result;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_pjimeno97_exerciseeditortool_MainActivity_addExercise(JNIEnv *env, jobject thiz,
                                                               jobject exerciseJava) {
    // Se carga la clase Exercise con sus métodos getter
    jclass exerciseJavaClass = env->FindClass("com/pjimeno97/exerciseeditortool/Exercise");
    jmethodID getName = env->GetMethodID(exerciseJavaClass, "getName", "()Ljava/lang/String;");
    jmethodID getDescription = env->GetMethodID(exerciseJavaClass, "getDescription", "()Ljava/lang/String;");
    jmethodID getUrlVideo = env->GetMethodID(exerciseJavaClass, "getUrlVideo", "()Ljava/lang/String;");
    jmethodID getMuscle = env->GetMethodID(exerciseJavaClass, "getMuscle", "()Ljava/lang/String;");

    // Se crea un ejercicio de la clase de c++ con los valores del objeto java
    Exercise exercise (jstring2string(env, (jstring )env->CallObjectMethod(exerciseJava, getName)), jstring2string(env, (jstring )env->CallObjectMethod(exerciseJava, getDescription)), jstring2string(env, (jstring )env->CallObjectMethod(exerciseJava, getUrlVideo)), jstring2string(env, (jstring )env->CallObjectMethod(exerciseJava, getMuscle)));
    Exercise_List::instance().add_exercise(exercise);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_pjimeno97_exerciseeditortool_MainActivity_editExercise(JNIEnv *env, jobject thiz,
                                                                jint position, jobject exerciseJava) {
    // Se carga la clase Exercise con sus métodos getter
    jclass exerciseJavaClass = env->FindClass("com/pjimeno97/exerciseeditortool/Exercise");
    jmethodID getName = env->GetMethodID(exerciseJavaClass, "getName", "()Ljava/lang/String;");
    jmethodID getDescription = env->GetMethodID(exerciseJavaClass, "getDescription", "()Ljava/lang/String;");
    jmethodID getUrlVideo = env->GetMethodID(exerciseJavaClass, "getUrlVideo", "()Ljava/lang/String;");
    jmethodID getMuscle = env->GetMethodID(exerciseJavaClass, "getMuscle", "()Ljava/lang/String;");

    // Se crea un ejercicio de la clase de c++ con los valores del objeto java
    Exercise exercise (jstring2string(env, (jstring )env->CallObjectMethod(exerciseJava, getName)), jstring2string(env, (jstring )env->CallObjectMethod(exerciseJava, getDescription)), jstring2string(env, (jstring )env->CallObjectMethod(exerciseJava, getUrlVideo)), jstring2string(env, (jstring )env->CallObjectMethod(exerciseJava, getMuscle)));
    Exercise_List::instance().edit_exercise(exercise, position);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_pjimeno97_exerciseeditortool_MainActivity_deleteExercise(JNIEnv *env, jobject thiz,
                                                                  jint position) {
    Exercise_List::instance().delete_exercise(position);
}