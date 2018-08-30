#ifndef RDRREADER9D00U_H_
#define RDRREADER9D00U_H_

#define EXPORTAPI extern "C" _declspec(dllimport) 

EXPORTAPI int  _stdcall RdrOpenPort(char *port);						//打开串口
EXPORTAPI int  _stdcall RdrClosePort(char *port);						//关闭串口
EXPORTAPI int  _stdcall RdrGroupReadEPC(char *port, char *outepccode); 	//读取EPC号
EXPORTAPI int  _stdcall RdrGroupReadTID(char *port, char *outtid);		//读取TID号
EXPORTAPI int  _stdcall RdrReadEPCByTID(char *port, char *intid, char *outepcdata);//读取内存中的EPC区数据
EXPORTAPI int  _stdcall RdrWriteEPCByTID(char *port, char *intid, char *inepcdata);//写内存中的EPC区数据
EXPORTAPI int  _stdcall RdrReadTIDByTID(char *port, char *intid, char *startaddr, int len, char *outtiddata);//读取内存中的TID区数据
EXPORTAPI int  _stdcall RdrReadUSRByTID(char *port, char *intid, char *startaddr, int len, char *outusrdata);//读取内存中的USR区数据
EXPORTAPI int  _stdcall RdrWriteUSRByTID(char *port, char *intid, char *startaddr, int len, char *inusrdata);//写内存中的USR区数据
EXPORTAPI bool  _stdcall GetCommEventError();

#endif