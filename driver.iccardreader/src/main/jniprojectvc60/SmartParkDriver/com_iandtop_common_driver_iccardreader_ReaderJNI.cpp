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
	//16��������ÿ������4���飬ÿ����16���ֽ�.
	//���ȵ�λΪ�ֽ�.
	//�����ʼλ��blk=2������len=32,��д��2,3��

	//��ȡ����
	BYTE *b_buf = (BYTE *)malloc(len*sizeof(BYTE));
	int r = M1_ReadData(handle,blk,b_buf,len,0,0);

	//ת��ΪjbyteArray����
	jbyte *by = (jbyte*)b_buf;  
	jbyteArray jarray = env->NewByteArray(len);  
	env->SetByteArrayRegion(jarray, 0, len, by);
	return jarray;
}

JNIEXPORT jint JNICALL Java_com_iandtop_common_driver_iccardreader_ReaderJNI_M1WriteData
  (JNIEnv* env, jclass, jint handle, jbyte blk, jbyteArray data,jbyteArray key)
{
	//ת��ΪBYTE*
	jbyte * olddata = (jbyte*)env->GetByteArrayElements(data, 0);  
	jsize  oldsize = env->GetArrayLength(data);  
	BYTE* bytearr = (BYTE*)olddata;  
	//��ȡ����
	int len = (int)oldsize;  
//	BYTE Key[6] = {0xff,0xff, 0xff, 0xff,0xff,0xff};//��Կ����6���ֽ� 12��F�� BYTE Key[6] = {0xff. 0xff. 0xff, 0xff,0xff,0xff}
	return M1_WriteData( handle , blk,  bytearr , len , 0 ,0 ) ;
}
