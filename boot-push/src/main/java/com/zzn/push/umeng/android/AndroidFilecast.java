package com.zzn.push.umeng.android;


import com.zzn.push.umeng.AbstractAndroidNotification;

/**
 * @author zengzhangni
 * @date 2019/3/14
 */
public class AndroidFilecast extends AbstractAndroidNotification {
	public AndroidFilecast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "filecast");	
	}
	
	public void setFileId(String fileId) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    }
}