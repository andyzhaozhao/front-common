 #ifndef __AXCARDDEAL_H__
#define __AXCARDDEAL_H__

/*
 *	宏	
 */
#ifndef DLLAPI
	#define DLLAPI	extern "C" __declspec(dllexport)
#endif

/*
 *	函数类型
 */
#ifndef DLLCALL
	#define DLLCALL	__stdcall
#endif

/*
 *	函数返回代码
 */
#define RET_SUCESS			0		/*	执行成功					*/
#define RET_TIMEOUT			-1	
#define RET_FAIL			-2		/*	执行失败					*/
#define RET_PARAMERR		-3		/*	参数格式不对				*/
#define RET_NOSUPPORT		-4		/*	此功能不支持				*/
#define RET_PASAMERR		-5		/*	PSAM卡通讯失败				*/

#define RET_ZEROOP			-8		/*	0操作						*/
#define RET_NOTSURE			-9		/*	临界条件,不确定是否成功		*/

typedef int DEVHANDLE ;

/*
 *	打开读头
 */
DLLAPI DEVHANDLE DLLCALL OpenReader( ) ;
DLLAPI void DLLCALL CloseReader( DEVHANDLE nDev ) ;


/*************************************************************************
 *	13.56M A类 寻卡指令
 */
// 寻卡
DLLAPI int DLLCALL requestCard( DEVHANDLE hDev , BYTE *cardType , UINT32 *phyNo ) ;
DLLAPI int DLLCALL requestCardEx( DEVHANDLE hDev , BYTE *phyId , BYTE *phyIdLen , BYTE *Atq , BYTE *requestCode ) ;

DLLAPI int DLLCALL M1_HaltCard( DEVHANDLE nDev ) ;

/**************************************************************************
 * 设置通讯方式 
 */
DLLAPI int DLLCALL setCommEncrypt( BOOL bEncrypt ) ;


/***************************************************************************
 *	M1 卡操作指令	
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
 *	其他指令
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
 *	CPU卡传送指令
 */
DLLAPI int DLLCALL iso14443_reset	( DEVHANDLE nDev , unsigned char *outbuf , unsigned int *outbufsize  ) ;
DLLAPI int DLLCALL iso14443_transceive( DEVHANDLE hDev , unsigned char *inbuf , unsigned int inbufsize , unsigned char *outbuf , unsigned int *outbufsize ) ;
DLLAPI int DLLCALL iso14443_halt	( DEVHANDLE hDev ) ;


/****************************************************************************
 *	PSAM卡 指令
 */
DLLAPI int DLLCALL iso7816_reset( DEVHANDLE hDev , int slot , unsigned char *outbuf , unsigned int *outbufsize  ) ;
DLLAPI int DLLCALL iso7816_transceive( DEVHANDLE hDev , int slot , unsigned char *inbuf , unsigned int inbufsize , unsigned char *outbuf , unsigned int *outbufsize ) ;


/****************************************************************************
 *	ZTE	指令
 */
DLLAPI int DLLCALL zte_transceive( DEVHANDLE hDev , unsigned char *inbuf , unsigned int inbufsize , unsigned char *outbuf , unsigned int *outbufsize ) ;


/****************************************************************************
 *	utils
 *		读数据 写数据 读版本 LED控制
 */
DLLAPI int DLLCALL string2hex( const char *str , unsigned char *hex , int hexlen ) ;
DLLAPI int DLLCALL buf2money( const unsigned char *buf , unsigned int *money ) ;
DLLAPI void DLLCALL money2buf( unsigned int money , unsigned char *buf ) ;

/*****************************************************************************
 *	卡格式操作
 */
/*
 *	参数
 */
DLLAPI int DLLCALL FormatSetParamVer( DEVHANDLE nDev , unsigned int paramver ) ;
DLLAPI int DLLCALL FormatGetParamVer( DEVHANDLE nDev , unsigned int *paramver ) ;

DLLAPI int DLLCALL FormatSetEmptyKey( DEVHANDLE nDev , unsigned char enctype , unsigned char key[8] ) ;

DLLAPI int DLLCALL Format11SetSects( DEVHANDLE nDev , int sectbase , int sectmoney , int sectctrl , int sectlist , int listcount ) ;
DLLAPI int DLLCALL Format11GetSects(  DEVHANDLE nDev , int *sectbase , int *sectmoney , int *sectctrl , int *sectlist , int *listcount );
DLLAPI int DLLCALL Format11SetMainKey(  DEVHANDLE nDev , const unsigned char *mainkey ) ;

/*
 *	卡片操作
 */
DLLAPI int DLLCALL FormatInitCard( DEVHANDLE nDev , __in const char * formatName  ) ;		/*	初始化卡片	*/
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
	DLLAPI unsigned int DLLCALL FormatGetDayLimit		( DEVHANDLE nDev ) ;	/*	日限额			*/
	DLLAPI unsigned int DLLCALL FormatGetConsumeLimit	( DEVHANDLE nDev ) ;	/*	单笔限额		*/
	DLLAPI unsigned int DLLCALL FormatGetLimitKey		( DEVHANDLE nDev ) ;	/*	超额密码		*/
	DLLAPI unsigned int DLLCALL FormatGetCorpId			( DEVHANDLE nDev ) ;	/*	企业代码		*/
	DLLAPI unsigned int DLLCALL FormatGetLastTradeDevNo ( DEVHANDLE nDev ) ;	/*	上一次交易的机号	*/

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
	DLLAPI int DLLCALL FormatSetDayLimit	( DEVHANDLE nDev , unsigned int dayconsumelimit ) ;		/*	日限额		*/
	DLLAPI int DLLCALL FormatSetConsumeLimit( DEVHANDLE nDev , unsigned int consumelimit ) ;		/*	单笔限额	*/
	DLLAPI int DLLCALL FormatSetLimitKey	( DEVHANDLE nDev , unsigned int limitkey	) ;			/*	超额密码	*/
	DLLAPI int DLLCALL FormatSetCorpId		( DEVHANDLE nDev , unsigned int corpid		) ;			/*	企业代码	*/
	DLLAPI int DLLCALL FormatSetBatch		( DEVHANDLE nDev , unsigned int batch ) ;		/*	修改补贴批次		*/

DLLAPI int DLLCALL FormatReduceMoney( DEVHANDLE nDev , unsigned short softtag , int WalletIndex , int money ) ;			
DLLAPI int DLLCALL FormatChargeMoney( DEVHANDLE nDev , unsigned short softtag , int WalletIndex , int money , int Batch , char *validDate ) ;


/*****************************************************************************
 *	des
 *		读数据 写数据 读版本 LED控制
 */
/*	DES加密
 */
DLLAPI int DLLCALL des_dec_dat ( unsigned char *pDat, int len, const unsigned char *pKey ) ;
DLLAPI int DLLCALL des_enc_dat ( unsigned char *pDat, int len, const unsigned char *pKey ) ;


/*	3Des加密
 */
DLLAPI int DLLCALL des3_enc_dat ( unsigned char *pDat, int len, const unsigned char *pKey);
DLLAPI int DLLCALL des3_dec_dat ( unsigned char *pDat, int len, const unsigned char *pKey);


/*
 *	分散
 */
DLLAPI int DLLCALL des3_diversify( const unsigned char *pDiversify,unsigned char *pDat) ;

/*
 * pboc 
 */
DLLAPI int DLLCALL pboc_des_mac( const unsigned char *ver , unsigned char *pDat , int len , const unsigned char *pKey , unsigned char *pMac ) ;
DLLAPI int DLLCALL pboc_des3_mac( const unsigned char *ver , unsigned char *pDat , int len , const unsigned char *pKey , unsigned char *pMac ) ;




#endif
