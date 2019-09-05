package com.zzn.push.umeng.android;


import com.zzn.push.umeng.AbstractAndroidNotification;

/**
 * @author zengzhangni
 * @date 2019/3/14
 */
public class AndroidUnicast extends AbstractAndroidNotification {
	public AndroidUnicast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "unicast");	
	}
	
	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }

}