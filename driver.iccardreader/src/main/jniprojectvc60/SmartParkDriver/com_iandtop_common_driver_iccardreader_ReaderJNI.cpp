#include "com_iandtop_common_driver_iccardreader_ReaderJNI.h"  
#include <windows.h>
#include <stdio.h>
#define __in
#define __in_z
#define __in_z_opt
#define __inout
#define __out

#include "AxCardDeal.h"

JNIEXPORT jstring JNICALL Java_com_iandtop_common_driver_iccardreader_ReaderJNI_getVersion
  (JNIEnv* env, jclass)
{
	const char* s = "2";
	return env->NewStringUTF(s);

}

JNIEXPORT jint JNICALL Java_com_iandtop_common_driver_iccardreader_ReaderJNI_openReader
  (JNIEnv *, jclass)
{
	//printf("openReader\n");
	int handle = OpenReader();
	return handle;
}


JNIEXPORT jint JNICALL Java_com_iandtop_common_driver_iccardreader_ReaderJNI_closeReader
  (JNIEnv *, jclass, jint handle)
{
	CloseReader(handle);
	return 0;
}

JNIEXPORT jint JNICALL Java_com_iandtop_common_driver_iccardreader_ReaderJNI_requestCard
  (JNIEnv *, jclass, jint handle)
{
	UINT32 phyNo;
	BYTE cardType = 1;
	int state = requestCard(handle,&cardType,&phyNo);
	//printf("result of requestCard=%d\n",state);
	printf("card integer=%ld\n",phyNo);
	return phyNo;
}

JNIEXPORT jint JNICALL Java_com_iandtop_common_driver_iccardreader_ReaderJNI_hidLed
  (JNIEnv *, jclass, jint handle, jint ms, jint count)
{
	return Hid_Led(handle,ms,count);
}

JNIEXPORT jint JNICALL Java_com_iandtop_common_driver_iccardreader_ReaderJNI_hidBeep
  (JNIEnv *, jclass, jint handle, jint ms, jint count)
{
	return Hid_Beep(handle,ms,count);
}

JNIEXPORT jbyteArray JNICALL Java_com_iandtop_common_driver_iccardreader_ReaderJNI_M1ReadData
  (JNIEnv* env, jclass, jint handle, jbyte blk, jint len, jbyteArray key)
{
	//16个扇区，每个扇区4个块，每个块16个字节.
	//长度单位为字节.
	//如果开始位置blk=2，长度len=32,则写了2,3块

	//读取数据
	BYTE *b_buf = (BYTE *)malloc(len*sizeof(BYTE));
	int r = M1_ReadData(handle,blk,b_buf,len,0,0);

	//转换为jbyteArray类型
	jbyte *by = (jbyte*)b_buf;  
	jbyteArray jarray = env->NewByteArray(len);  
	env->SetByteArrayRegion(jarray, 0, len, by);
	return jarray;
}

JNIEXPORT jint JNICALL Java_com_iandtop_common_driver_iccardreader_ReaderJNI_M1WriteData
  (JNIEnv* env, jclass, jint handle, jbyte blk, jbyteArray data,jbyteArray key)
{
	//转换为BYTE*
	jbyte * olddata = (jbyte*)env->GetByteArrayElements(data, 0);  
	jsize  oldsize = env->GetArrayLength(data);  
	BYTE* bytearr = (BYTE*)olddata;  
	//获取长度
	int len = (int)oldsize;  
//	BYTE Key[6] = {0xff,0xff, 0xff, 0xff,0xff,0xff};//密钥都是6个字节 12个F是 BYTE Key[6] = {0xff. 0xff. 0xff, 0xff,0xff,0xff}
	return M1_WriteData( handle , blk,  bytearr , len , 0 ,0 ) ;
}
