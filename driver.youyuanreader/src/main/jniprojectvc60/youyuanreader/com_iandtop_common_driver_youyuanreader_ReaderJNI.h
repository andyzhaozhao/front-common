/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_iandtop_common_driver_youyuanreader_ReaderJNI */

#ifndef _Included_com_iandtop_common_driver_youyuanreader_ReaderJNI
#define _Included_com_iandtop_common_driver_youyuanreader_ReaderJNI
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_iandtop_common_driver_youyuanreader_ReaderJNI
 * Method:    getVersion
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_iandtop_common_driver_youyuanreader_ReaderJNI_getVersion
  (JNIEnv *, jclass);

/*
 * Class:     com_iandtop_common_driver_youyuanreader_ReaderJNI
 * Method:    openPort
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_iandtop_common_driver_youyuanreader_ReaderJNI_openPort
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_iandtop_common_driver_youyuanreader_ReaderJNI
 * Method:    closePort
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_iandtop_common_driver_youyuanreader_ReaderJNI_closePort
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_iandtop_common_driver_youyuanreader_ReaderJNI
 * Method:    readEPC
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_iandtop_common_driver_youyuanreader_ReaderJNI_readEPC
  (JNIEnv *, jclass, jstring);

#ifdef __cplusplus
}
#endif
#endif
