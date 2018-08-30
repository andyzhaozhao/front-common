 #ifndef __AXCARDDEAL_H__
#define __AXCARDDEAL_H__

/*
 *	��	
 */
#ifndef DLLAPI
	#define DLLAPI	extern "C" __declspec(dllexport)
#endif

/*
 *	��������
 */
#ifndef DLLCALL
	#define DLLCALL	__stdcall
#endif

/*
 *	�������ش���
 */
#define RET_SUCESS			0		/*	ִ�гɹ�					*/
#define RET_TIMEOUT			-1	
#define RET_FAIL			-2		/*	ִ��ʧ��					*/
#define RET_PARAMERR		-3		/*	������ʽ����				*/
#define RET_NOSUPPORT		-4		/*	�˹��ܲ�֧��				*/
#define RET_PASAMERR		-5		/*	PSAM��ͨѶʧ��				*/

#define RET_ZEROOP			-8		/*	0����						*/
#define RET_NOTSURE			-9		/*	�ٽ�����,��ȷ���Ƿ�ɹ�		*/

typedef int DEVHANDLE ;

/*
 *	�򿪶�ͷ
 */
DLLAPI DEVHANDLE DLLCALL OpenReader( ) ;
DLLAPI void DLLCALL CloseReader( DEVHANDLE nDev ) ;


/*************************************************************************
 *	13.56M A�� Ѱ��ָ��
 */
// Ѱ��
DLLAPI int DLLCALL requestCard( DEVHANDLE hDev , BYTE *cardType , UINT32 *phyNo ) ;
DLLAPI int DLLCALL requestCardEx( DEVHANDLE hDev , BYTE *phyId , BYTE *phyIdLen , BYTE *Atq , BYTE *requestCode ) ;

DLLAPI int DLLCALL M1_HaltCard( DEVHANDLE nDev ) ;

/**************************************************************************
 * ����ͨѶ��ʽ 
 */
DLLAPI int DLLCALL setCommEncrypt( BOOL bEncrypt ) ;


/***************************************************************************
 *	M1 ������ָ��	
 */
#define AUTH_KEYA	1
#define AUTH_KEYB	0
DLLAPI int DLLCALL M1_Auth		(	DEVHANDLE hDev , BYTE Blk  , BYTE Mode	, const BYTE *Key	) ;
DLLAPI int DLLCALL M1_ReadBlk	(	DEVHANDLE hDev , BYTE Blk	, BYTE *Buf			) ;
DLLAPI int DLLCALL M1_WriteBlk	(	DEVHANDLE hDev , BYTE Blk	, const BYTE *Buf	) ;
DLLAPI int DLLCALL M1_ReadValue(	DEVHANDLE nDev , BYTE Blk	, UINT32 *Value		) ;
DLLAPI int DLLCALL M1_WriteValue(	DEVHANDLE hDev , BYTE Blk	, UINT32 Value		) ;
DLLAPI int DLLCALL M1_Inc		(	DEVHANDLE hDev , BYTE Blk	, UINT32 Value		) ;
DLLAPI int DLLCALL M1_Dec		(	DEVHANDLE hDev , BYTE Blk	, UINT32 Value		) ;
DLLAPI int DLLCALL M1_Copy		(	DEVHANDLE hDev , BYTE BlkTo , BYTE BlkFrom		) ; 

DLLAPI int DLLCALL M1_ReadData (	DEVHANDLE hDev , BYTE Blk , BYTE *Buf , INT readlen , BYTE Mode , const BYTE *Key ) ;
DLLAPI int DLLCALL M1_WriteData(	DEVHANDLE hDev , BYTE Blk , const BYTE *Buf , INT readlen , BYTE Mode , const BYTE *Key ) ;

DLLAPI int DLLCALL M1_SetControl( unsigned char *keybuf , unsigned char p0 , unsigned char p1 , unsigned char p2 , unsigned char p3 ) ;
DLLAPI int DLLCALL M1_GetControl( unsigned char *keybuf , unsigned char *p0 , unsigned char *p1 , unsigned char *p2 , unsigned char *p3 ) ;


/***************************************************************************
 *
 *	����ָ��
 */
DLLAPI int DLLCALL Hid_ReadBuf( DEVHANDLE hDev , BYTE Index , BYTE *Buf ) ;
DLLAPI int DLLCALL Hid_WriteBuf( DEVHANDLE hDev , BYTE Index , const BYTE *Buf ) ;

DLLAPI int DLLCALL Hid_Led( DEVHANDLE hDev  , unsigned int ms , int count ) ;
DLLAPI int DLLCALL Hid_Beep( DEVHANDLE hDev  , unsigned int ms , int count ) ;


DLLAPI int DLLCALL Hid_GetSeq( DEVHANDLE hDev , unsigned char seq[8] , unsigned short *devid ) ;
DLLAPI int DLLCALL Hid_SetSeq( DEVHANDLE hDev , const unsigned char seq[8] , unsigned short devid ) ;

DLLAPI int DLLCALL SetKeyBoard( DEVHANDLE hDev ,BOOL bStart ) ;
DLLAPI int DLLCALL GetKeyBoard( DEVHANDLE hDev ,BYTE *key , BYTE *keylen , BOOL *bConfirm ) ;

/***************************************************************************
 *	CPU������ָ��
 */
DLLAPI int DLLCALL iso14443_reset	( DEVHANDLE nDev , unsigned char *outbuf , unsigned int *outbufsize  ) ;
DLLAPI int DLLCALL iso14443_transceive( DEVHANDLE hDev , unsigned char *inbuf , unsigned int inbufsize , unsigned char *outbuf , unsigned int *outbufsize ) ;
DLLAPI int DLLCALL iso14443_halt	( DEVHANDLE hDev ) ;


/****************************************************************************
 *	PSAM�� ָ��
 */
DLLAPI int DLLCALL iso7816_reset( DEVHANDLE hDev , int slot , unsigned char *outbuf , unsigned int *outbufsize  ) ;
DLLAPI int DLLCALL iso7816_transceive( DEVHANDLE hDev , int slot , unsigned char *inbuf , unsigned int inbufsize , unsigned char *outbuf , unsigned int *outbufsize ) ;


/****************************************************************************
 *	ZTE	ָ��
 */
DLLAPI int DLLCALL zte_transceive( DEVHANDLE hDev , unsigned char *inbuf , unsigned int inbufsize , unsigned char *outbuf , unsigned int *outbufsize ) ;


/****************************************************************************
 *	utils
 *		������ д���� ���汾 LED����
 */
DLLAPI int DLLCALL string2hex( const char *str , unsigned char *hex , int hexlen ) ;
DLLAPI int DLLCALL buf2money( const unsigned char *buf , unsigned int *money ) ;
DLLAPI void DLLCALL money2buf( unsigned int money , unsigned char *buf ) ;

/*****************************************************************************
 *	����ʽ����
 */
/*
 *	����
 */
DLLAPI int DLLCALL FormatSetParamVer( DEVHANDLE nDev , unsigned int paramver ) ;
DLLAPI int DLLCALL FormatGetParamVer( DEVHANDLE nDev , unsigned int *paramver ) ;

DLLAPI int DLLCALL FormatSetEmptyKey( DEVHANDLE nDev , unsigned char enctype , unsigned char key[8] ) ;

DLLAPI int DLLCALL Format11SetSects( DEVHANDLE nDev , int sectbase , int sectmoney , int sectctrl , int sectlist , int listcount ) ;
DLLAPI int DLLCALL Format11GetSects(  DEVHANDLE nDev , int *sectbase , int *sectmoney , int *sectctrl , int *sectlist , int *listcount );
DLLAPI int DLLCALL Format11SetMainKey(  DEVHANDLE nDev , const unsigned char *mainkey ) ;

/*
 *	��Ƭ����
 */
DLLAPI int DLLCALL FormatInitCard( DEVHANDLE nDev , __in const char * formatName  ) ;		/*	��ʼ����Ƭ	*/
DLLAPI int DLLCALL FormatRecyleCard( DEVHANDLE nDev , __in const char * formatName ) ;

DLLAPI int DLLCALL FormatReadCard( DEVHANDLE nDev , __in const char * formatName  ) ;
	DLLAPI unsigned int DLLCALL FormatGetCardNo			(  DEVHANDLE nDev ) ;
	DLLAPI char *		DLLCALL FormatGetCardNoString	( DEVHANDLE nDev ) ;
	DLLAPI int			DLLCALL FormatGetCardCls		( DEVHANDLE nDev ) ;
	DLLAPI int			DLLCALL FormatGetCardStatus		( DEVHANDLE nDev ) ;
	DLLAPI int			DLLCALL FormatGetBlackCount		( DEVHANDLE nDev ) ;
	DLLAPI char *		DLLCALL FormatGetName			( DEVHANDLE nDev ) ;
	DLLAPI char *		DLLCALL FormatGetWorkNo			( DEVHANDLE nDev ) ;
	DLLAPI int  		DLLCALL	FormatGetValidDate		( DEVHANDLE nDev , char startDate[11] , char endDate[11] ) ;
	DLLAPI unsigned int DLLCALL FormatGetDayLimit		( DEVHANDLE nDev ) ;	/*	���޶�			*/
	DLLAPI unsigned int DLLCALL FormatGetConsumeLimit	( DEVHANDLE nDev ) ;	/*	�����޶�		*/
	DLLAPI unsigned int DLLCALL FormatGetLimitKey		( DEVHANDLE nDev ) ;	/*	��������		*/
	DLLAPI unsigned int DLLCALL FormatGetCorpId			( DEVHANDLE nDev ) ;	/*	��ҵ����		*/
	DLLAPI unsigned int DLLCALL FormatGetLastTradeDevNo ( DEVHANDLE nDev ) ;	/*	��һ�ν��׵Ļ���	*/

	DLLAPI int			DLLCALL FormatGetStat	( DEVHANDLE nDev , char lastdate[11] , unsigned int *daytotal , int *daycount ,int *sectid , int *sectcount ) ;
	DLLAPI int			DLLCALL FormatGetEWallet( DEVHANDLE nDev ,  int walletIndex , unsigned int *Money ,  unsigned int *Count , char *validDate , int *Batch ) ;

DLLAPI int DLLCALL FormatWriteCard( DEVHANDLE nDev ) ;
	DLLAPI int DLLCALL FormatSetCardNo		( DEVHANDLE nDev , unsigned int cardno	) ;
	DLLAPI int DLLCALL FormatSetCardCls		( DEVHANDLE nDev , int cardcls			) ;
	DLLAPI int DLLCALL FormatSetCardStatus	( DEVHANDLE nDev , int cardstatus		) ;
	DLLAPI int DLLCALL FormatSetBlackCount	( DEVHANDLE nDev , int blackcount		) ;
	DLLAPI int DLLCALL FormatSetName		( DEVHANDLE nDev , const char *name		) ;
	DLLAPI int DLLCALL FormatSetWorkNo		( DEVHANDLE nDev , const char *workno	) ;
	DLLAPI int DLLCALL FormatSetStartDate	( DEVHANDLE nDev , const char *startDate ) ;
	DLLAPI int DLLCALL FormatSetEndDate		( DEVHANDLE nDev , const char *endDate	 ) ;
	DLLAPI int DLLCALL FormatSetDayLimit	( DEVHANDLE nDev , unsigned int dayconsumelimit ) ;		/*	���޶�		*/
	DLLAPI int DLLCALL FormatSetConsumeLimit( DEVHANDLE nDev , unsigned int consumelimit ) ;		/*	�����޶�	*/
	DLLAPI int DLLCALL FormatSetLimitKey	( DEVHANDLE nDev , unsigned int limitkey	) ;			/*	��������	*/
	DLLAPI int DLLCALL FormatSetCorpId		( DEVHANDLE nDev , unsigned int corpid		) ;			/*	��ҵ����	*/
	DLLAPI int DLLCALL FormatSetBatch		( DEVHANDLE nDev , unsigned int batch ) ;		/*	�޸Ĳ�������		*/

DLLAPI int DLLCALL FormatReduceMoney( DEVHANDLE nDev , unsigned short softtag , int WalletIndex , int money ) ;			
DLLAPI int DLLCALL FormatChargeMoney( DEVHANDLE nDev , unsigned short softtag , int WalletIndex , int money , int Batch , char *validDate ) ;


/*****************************************************************************
 *	des
 *		������ д���� ���汾 LED����
 */
/*	DES����
 */
DLLAPI int DLLCALL des_dec_dat ( unsigned char *pDat, int len, const unsigned char *pKey ) ;
DLLAPI int DLLCALL des_enc_dat ( unsigned char *pDat, int len, const unsigned char *pKey ) ;


/*	3Des����
 */
DLLAPI int DLLCALL des3_enc_dat ( unsigned char *pDat, int len, const unsigned char *pKey);
DLLAPI int DLLCALL des3_dec_dat ( unsigned char *pDat, int len, const unsigned char *pKey);


/*
 *	��ɢ
 */
DLLAPI int DLLCALL des3_diversify( const unsigned char *pDiversify,unsigned char *pDat) ;

/*
 * pboc 
 */
DLLAPI int DLLCALL pboc_des_mac( const unsigned char *ver , unsigned char *pDat , int len , const unsigned char *pKey , unsigned char *pMac ) ;
DLLAPI int DLLCALL pboc_des3_mac( const unsigned char *ver , unsigned char *pDat , int len , const unsigned char *pKey , unsigned char *pMac ) ;




#endif
