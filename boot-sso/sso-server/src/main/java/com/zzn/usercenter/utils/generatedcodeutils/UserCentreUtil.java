/**
 *
 */
package com.zzn.usercenter.utils.generatedcodeutils;

/**
 * @author liyang
 * @version 1.0 2019年3月7日
 */
public class UserCentreUtil {

    public static String getUserCentreCode() {
        String user = "BP20" + DateUtil.formatTimeForyyMMddHHmmss();
        return CreateNo.getInstance().GenerateNo(user, 4);
    }


}
