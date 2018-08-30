import com.iandtop.common.driver.DoorDriver;
import com.iandtop.common.driver.door.DriverTCPClient;
import io.vertx.core.Vertx;

import java.io.IOException;

/**
 * Created by Administrator on 2016/9/30.
 */
public class Test {
    public static void main(String args[]){
        System.out.println("sdfsdfsd");
        DriverTCPClient ttt = DoorDriver.getInstance(Vertx.vertx(), "192.168.1.150", 8000, "CA-3220T26060976");
        try {
            ttt.readSN(sn->{
                System.out.println(sn);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void test() throws IOException {

        DriverTCPClient ttt = new DriverTCPClient(Vertx.vertx(), "192.168.1.159", 8000, "CA-3220T26060976");
//        ttt.readDoorOpenTime(1,time->{
//            System.out.println("opendoortime is "+time);
//        });

//        ttt.openDoor(true,false,false ,false , r->{
//            System.out.println("opendoortime is "+r);
//        });

//       ttt.readCardReaderInfo(vo->{
//            System.out.println(vo);
//        });

        ttt.readMonitorState(sn -> {
            System.out.println(sn);
            try {
                ttt.readDeviceDataWorkMode(1, vo -> {
                    System.out.println(vo);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


//		getSn("192.168.1.150", 8000);

//		 openDoor("192.168.1.150", 8000, "CA-3240T46040235", true, false,
//		 false, false);

        // closeDoor("192.168.1.150", 8000, "CA-3240T46040235", true, false,
        // false, false);

        // deviceInfo("192.168.1.150", 8000, "CA-3240T46040235");

        //readDeviceTime("192.168.1.150", 8000, "CA-3240T46040235");

        //writeDeviceTime("192.168.1.150", 8000, "CA-3240T46040235", new Date());

/*		 writeCardReaderInfo("192.168.1.150", 8000, "CA-3240T46040235",
         DeviceDataCardReaderVO.CardReaderType_wg34,
		 DeviceDataCardReaderVO.CardReaderType_wg34,
		 DeviceDataCardReaderVO.CardReaderType_wg34,
		 DeviceDataCardReaderVO.CardReaderType_wg34);
*/

//		 readCardReaderInfo("192.168.1.150", 8000, "CA-3240T46040235");

//		 deviceDataWorkMode("192.168.1.150", 8000, "CA-3240T46040235",1);

/*		 long a = readDoorOpenTime("192.168.1.150", 8000,
         "CA-3240T46040235",1);
		 System.out.println(a);*/

//		 writeDoorOpenTime("192.168.1.150", 8000, "CA-3240T46040235", 1,5);

//		 readTimeGroup("192.168.1.150", 8000, "CA-3240T46040235");

//        DeviceDataTimeGrpSegmentVO svo = new DeviceDataTimeGrpSegmentVO(
//                "01:01:01-12:12:12", "01:01:01-12:12:12", "01:01:01-12:12:12", "01:01:01-12:12:12",
//                "01:01:01-12:12:12", "01:01:01-12:12:12", "01:01:01-12:12:12", "01:01:01-12:12:12");
//
//        DeviceDataTimeGrpWeekVO vo = new DeviceDataTimeGrpWeekVO(
//                svo, svo, svo, svo, svo, svo, svo);
//        Boolean ok = writeTimeGroup("192.168.1.150", 8000, "CA-3240T46040235", 64, vo);
    }
}
