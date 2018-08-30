#ifndef RDRREADER9D00U_H_
#define RDRREADER9D00U_H_

#define EXPORTAPI extern "C" _declspec(dllimport) 

EXPORTAPI int  _stdcall RdrOpenPort(char *port);						//�򿪴���
EXPORTAPI int  _stdcall RdrClosePort(char *port);						//�رմ���
EXPORTAPI int  _stdcall RdrGroupReadEPC(char *port, char *outepccode); 	//��ȡEPC��
EXPORTAPI int  _stdcall RdrGroupReadTID(char *port, char *outtid);		//��ȡTID��
EXPORTAPI int  _stdcall RdrReadEPCByTID(char *port, char *intid, char *outepcdata);//��ȡ�ڴ��е�EPC������
EXPORTAPI int  _stdcall RdrWriteEPCByTID(char *port, char *intid, char *inepcdata);//д�ڴ��е�EPC������
EXPORTAPI int  _stdcall RdrReadTIDByTID(char *port, char *intid, char *startaddr, int len, char *outtiddata);//��ȡ�ڴ��е�TID������
EXPORTAPI int  _stdcall RdrReadUSRByTID(char *port, char *intid, char *startaddr, int len, char *outusrdata);//��ȡ�ڴ��е�USR������
EXPORTAPI int  _stdcall RdrWriteUSRByTID(char *port, char *intid, char *startaddr, int len, char *inusrdata);//д�ڴ��е�USR������
EXPORTAPI bool  _stdcall GetCommEventError();

#endif