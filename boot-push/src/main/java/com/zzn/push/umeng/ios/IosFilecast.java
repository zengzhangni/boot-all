package com.zzn.push.umeng.ios;


import com.zzn.push.umeng.AbstractIosNotification;

/**
 * @author zengzhangni
 * @date 2019/3/14
 */
public class IosFilecast extends AbstractIosNotification {
	public IosFilecast(String appkey, String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "filecast");	
	}
	
	public void setFileId(String fileId) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    }
}
