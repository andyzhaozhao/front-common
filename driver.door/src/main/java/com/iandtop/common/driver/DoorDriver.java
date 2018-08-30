package com.iandtop.common.driver;

import com.iandtop.common.driver.door.DriverTCPClient;
import com.iandtop.common.driver.vo.RecordDataVO;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.HashMap;
import java.util.Map;

/**
 * 门禁对外驱动程序
 */
public class DoorDriver {
    private static Map<String,DriverTCPClient> Pool = new HashMap<>();

    public static DriverTCPClient getInstance(Vertx _vertx, String _deviceUrl, int _devicePort, String _deviceSn){
        DriverTCPClient in = Pool.get(_deviceUrl);
        if(in==null){
            in = new DriverTCPClient(_vertx,_deviceUrl,_devicePort,_deviceSn);
            Pool.put(_deviceUrl,in);
        }

        return in;
    }

    public static DriverTCPClient setRecordHandler(DriverTCPClient driverTCPClient,Handler<RecordDataVO> _recordHandler){
        driverTCPClient.setRecordHandler(_recordHandler);
        return driverTCPClient;
    }

}
