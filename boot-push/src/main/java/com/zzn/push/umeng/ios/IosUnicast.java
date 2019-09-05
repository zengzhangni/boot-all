package com.zzn.push.umeng.ios;


import com.zzn.push.umeng.AbstractIosNotification;

/**
 * @author zengzhangni
 * @date 2019/3/14
 */
public class IosUnicast extends AbstractIosNotification {
	public IosUnicast(String appkey, String appMasterSecret) throws Exception{
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "unicast");	
	}
	
	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }
}
