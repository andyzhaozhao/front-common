package com.iandtop.common.utils;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * license公共类
 */
public class LicenseUtil {

    public Boolean isSecure() throws Exception {
       // Properties prop = CommonFileUtil.getPropertyFile(licenseFile);
        InputStream in = this.getClass().getResourceAsStream("a.lie");
        Properties prop = new Properties();
        prop.load(in);
        Long d = Long.parseLong(prop.getProperty("d").trim());

        Date rdateD = DateUtils.parseDate(prop.getProperty("t").trim());
        Date nowdateD = DateUtils.now();

        int daysBetween = DateUtils.daysBetween(rdateD, nowdateD);
        if (daysBetween > d) {//超过试用期
            return false;
        } else {
            return true;
        }
    }
}


