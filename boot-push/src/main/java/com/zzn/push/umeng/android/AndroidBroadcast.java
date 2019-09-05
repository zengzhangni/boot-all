package com.zzn.push.umeng.android;


import com.zzn.push.umeng.AbstractAndroidNotification;

/**
 * @author zengzhangni
 * @date 2019/3/14
 */
public class AndroidBroadcast extends AbstractAndroidNotification {
	public AndroidBroadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");	
	}
}
