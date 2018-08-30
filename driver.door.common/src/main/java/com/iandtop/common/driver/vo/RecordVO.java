package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 记录指针信息
 */
public class RecordVO {

    private static final int recordDataLength = 4 + 13;
    private byte[] recordNum = new byte[4];//记录数
    //private List<byte[]> record = new ArrayList<byte[]>();//记录序号+记录内容：4+13
    private List<RecordDataVO> recordDataVOs = new ArrayList<RecordDataVO>();//记录序号+记录内容：4+13

    private Integer recordNumInt;

    public RecordVO(MessageVO messageVO) {
        byte[] message = messageVO.getData();
        recordNum = BinaryUtil.subArray(message, 0, 4);
        recordNumInt = BinaryUtil.byteToIntHignInF(recordNum);
        //int length = recordDataLength*recordNumInt;
        if (message.length > 4) {
            byte[] contentBytes = BinaryUtil.subArray(message, 4, message.length);

            for (int i = 0; i < contentBytes.length; i += recordDataLength) {
                byte[] cc = BinaryUtil.subArray(contentBytes, i + 4, i + recordDataLength);
                RecordDataVO dataVO = new RecordDataVO(cc);
                recordDataVOs.add(dataVO);
            }
        }
    }

    public static int getRecordDataLength() {
        return recordDataLength;
    }

    public byte[] getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(byte[] recordNum) {
        this.recordNum = recordNum;
    }

    public List<RecordDataVO> getRecordDataVOs() {
        return recordDataVOs;
    }

    public void setRecordDataVOs(List<RecordDataVO> recordDataVOs) {
        this.recordDataVOs = recordDataVOs;
    }

    public Integer getRecordNumInt() {
        return recordNumInt;
    }

    public void setRecordNumInt(Integer recordNumInt) {
        this.recordNumInt = recordNumInt;
    }
}
