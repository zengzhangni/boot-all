package com.zzn.push.umeng.ios;


import com.zzn.push.umeng.AbstractIosNotification;

/**
 * @author zengzhangni
 * @date 2019/3/14
 */
public class IosBroadcast extends AbstractIosNotification {
	public IosBroadcast(String appkey, String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");	
		
	}
}
