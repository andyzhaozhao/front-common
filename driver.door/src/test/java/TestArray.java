import com.iandtop.common.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

public class TestArray {
    public static void main(String[] args) {
        List<String> tarArr = new ArrayList<String>();
        tarArr.add("a");
        tarArr.add("b");
        tarArr.add("c");
        tarArr.add("d");
        tarArr.add("e");
        tarArr.add("f");
        tarArr.add("g");
        tarArr.add("h");

        List<List<String>> result = FormatUtils.splitList(tarArr,3);

        for(List<String> subArr:result) {
            for(String str:subArr) {
                System.out.println(str);
            }
        }



    }

}