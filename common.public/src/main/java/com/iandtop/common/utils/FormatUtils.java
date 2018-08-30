package com.iandtop.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式化工具类
 * @author 吕召
 */
public class FormatUtils {

    public static boolean checkNum(String str){

        Matcher mer = Pattern.compile("^[0-9]+$").matcher(str);
        return mer.find();

    }


    /**
     * 将list拆分
     * @param targe
     * @param size 每一块的大小
     * @return
     */
    public static List splitList(List targe, int size) {
        List<List<String>> listArr = new ArrayList<List<String>>();
        //获取被拆分的数组个数
        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
        for(int i=0;i<arrSize;i++) {
            List  sub = new ArrayList();
            //把指定索引数据放入到list中
            for(int j=i*size;j<=size*(i+1)-1;j++) {
                if(j<=targe.size()-1) {
                    sub.add(targe.get(j));
                }
            }
            listArr.add(sub);
        }
        return listArr;
    }

    public static void main(String[] main){
        System.out.println(checkNum("121"));
    }

}