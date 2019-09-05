package com.zzn.push.umeng;


import com.zzn.push.model.MemberUmeng;
import com.zzn.push.model.PushHistory;
import com.zzn.push.model.PushTemplate;
import com.zzn.push.umeng.android.AndroidUnicast;
import com.zzn.push.umeng.ios.IosUnicast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.zzn.push.umeng.AbstractAndroidNotification.AfterOpenAction.go_custom;


/**
 * @author zengzhangni
 * @date 2019/3/14
 */
public class UmengPushMsg {

    /**
     * 创建发送Client
     */
    private PushClient client = new PushClient();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 友盟推送消息记录
     */
    public PushHistory pushMsg(PushTemplate pushTemplate, String appKey, String appMasterSecret, String profiles, MemberUmeng memberUmeng) {
        PushHistory history = new PushHistory();
        String token = memberUmeng.getDeviceToken();
        try {
            if (token != null && !"".equals(token)) {
                //android的token长度是44
                if (token.length() == 44) {
                    history = this.pushMsgByAndroid(pushTemplate, appKey, appMasterSecret, profiles, memberUmeng);
                } else if (token.length() == 66) {
                    history = this.pushMsgByIos(pushTemplate, appKey, appMasterSecret, profiles, memberUmeng, token);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return history;
    }


    /**
     * 推送消息Android
     */
    private PushHistory pushMsgByAndroid(PushTemplate pushTemplate, String appKey, String appMasterSecret, String profiles,MemberUmeng memberUmeng) throws Exception {
        System.out.println(appKey);
        System.out.println(appMasterSecret);
        PushHistory bupHistory;
        //android通知
        AndroidUnicast unicastNotificat = new AndroidUnicast(appKey, appMasterSecret);
        unicastNotificat.setDeviceToken(memberUmeng.getDeviceToken());
        unicastNotificat.setDisplayType(AbstractAndroidNotification.DisplayType.NOTIFICATION);
        if (profiles.equals("pro")) {
            unicastNotificat.setProductionMode();
        } else {
            unicastNotificat.setTestMode();
        }
        unicastNotificat.setDescription(pushTemplate.getTemplateDescription());
        //android必填
        unicastNotificat.setTicker(pushTemplate.getTemplateName());
        unicastNotificat.setTitle(pushTemplate.getPushTitle());
        unicastNotificat.setText(pushTemplate.getTemplateContent());
        unicastNotificat.setAfterOpenAction(go_custom);
        int bruNotifi = client.send(unicastNotificat);
        if (bruNotifi == 200) {
            bupHistory = this.insertUmengMsg(pushTemplate, memberUmeng, 1);
        } else {
            bupHistory = null;
            System.out.println("推送失败......");
        }
        return bupHistory;
    }

    /**
     * 推送消息IOS
     */
    private PushHistory pushMsgByIos(PushTemplate pushTemplate, String appKey, String appMasterSecret, String profiles, MemberUmeng memberUmeng, String token) throws Exception {
        PushHistory bupHistory;
        //推送消息者(0--用户 1--邦女郎 2--店长)
        IosUnicast unicast = new IosUnicast(appKey, appMasterSecret);
        //推送消息
        unicast.setDeviceToken(token);
        unicast.setAlert(pushTemplate.getPushTitle());
        unicast.setBadge(0);
        unicast.setSound("default");
        if (profiles.equals("pro")) {
            unicast.setProductionMode();
        } else {
            unicast.setTestMode();
        }
        unicast.setCustomizedField("custom", pushTemplate.getTemplateContent());
        int bruIosMsg = client.send(unicast);
        if (bruIosMsg == 200) {
            bupHistory = this.insertUmengMsg(pushTemplate, memberUmeng, 2);
        } else {
            System.out.println("Ios deviceToken :" + token + ",Failed to send the notification,the time:" + sdf.format(new Date()));
            bupHistory = null;
        }
        return bupHistory;
    }


    /**
     * 添加umeng推送消息记录
     */
    public PushHistory insertUmengMsg(PushTemplate pushTemplate, MemberUmeng memberUmeng, Integer deviceType) {
        //友盟推送消息记录
        PushHistory history = new PushHistory();
        history.setTemplateCode(pushTemplate.getTemplateCode());
        history.setMsgType(pushTemplate.getPushType());
        history.setMemberUuid(memberUmeng.getMemberUuid());
        history.setPushTitle(pushTemplate.getPushTitle());
        history.setCustom(pushTemplate.getTemplateContent());
        history.setPlatformCode(pushTemplate.getPlatformCode());
        history.setDeviceToken(memberUmeng.getDeviceToken());
        history.setDeviceType(deviceType);
        return history;
    }

}
