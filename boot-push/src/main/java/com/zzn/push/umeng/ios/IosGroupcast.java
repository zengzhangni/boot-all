package com.zzn.push.umeng.ios;

import com.zzn.push.umeng.AbstractIosNotification;
import org.json.JSONObject;

/**
 * @author zengzhangni
 * @date 2019/3/14
 */
public class IosGroupcast extends AbstractIosNotification {
	public IosGroupcast(String appkey, String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "groupcast");	
	}
	
	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
