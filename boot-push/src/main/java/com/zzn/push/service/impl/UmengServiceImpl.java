package com.zzn.push.service.impl;

import com.zzn.push.config.*;
import com.zzn.push.model.MemberUmeng;
import com.zzn.push.model.PushHistory;
import com.zzn.push.model.PushMsgParamVo;
import com.zzn.push.model.PushTemplate;
import com.zzn.push.service.UmengService;
import com.zzn.push.umeng.UmengPushMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/3/14
 */
@Service
public class UmengServiceImpl implements UmengService {

    @Value(value = "${push.umeng.profiles}")
    private String profiles;

//    @Resource
//    private MemberUmengMapper memberUmengMapper;
//    @Resource
//    private PushHistoryMapper pushHistoryMapper;
//    @Resource
//    private PushTemplateMapper pushTemplateMapper;

    @Autowired
    private ConsumerAndroidProperties consumerAndroidProperties;
    @Autowired
    private BeauticianAndroidProperties beauticianAndroidProperties;
    @Autowired
    private ManagerAndroidProperties managerAndroidProperties;

    @Autowired
    private ConsumerIosProperties consumerIosProperties;
    @Autowired
    private BeauticianIosProperties beauticianIosProperties;
    @Autowired
    private ManagerIosProperties managerIosProperties;
    @Resource
    private UmengProperties umengProperties;
    //  用户
//        String appKey = "5a6bed91f29d9865100000e2";
//        String appMasterSecret = "424t4icy1g0jnttwmhlrk1smz0qzypka";

//    // 店长
//    String appKey = "5a6bef41f43e481d96000192";
//    String appMasterSecret = "yfc8frnhzkhsdedeb8opx0icakvujf6h";

// 邦女郎
//    String appKey = "5a6befd1f43e4879940000cf";
//    String appMasterSecret = "l8ztz3cv3ecdjhvcmwrdklhbijx5jmi8";


    @Override
    public ResponseMessage pushMessage(List<PushMsgParamVo> pushMsgParamVoList) {
        PushHistory pushHistory = null;
        for (PushMsgParamVo pushMsgParamVo : pushMsgParamVoList) {
            PushTemplate pushTemplate = this.paramCheck(pushMsgParamVo);
            if (pushTemplate != null) {
//                MemberUmeng memberUmeng = this.memberUmengMapper.queryByMemberUuid(pushMsgParamVo.getMemberUuid());
                MemberUmeng memberUmeng = new MemberUmeng();
                if (memberUmeng != null) {
                    String appKey = null;
                    String appMasterSecret = null;
                    if (pushMsgParamVo.getPushObject() == 3) {
                        if (memberUmeng.getDeviceToken().length() == 44) {
                            appKey = managerAndroidProperties.getAppKey();
                            appMasterSecret = managerAndroidProperties.getAppMasterSecret();
                        } else if (memberUmeng.getDeviceToken().length() == 64) {
                            appKey = managerIosProperties.getAppKey();
                            appMasterSecret = managerIosProperties.getAppMasterSecret();
                        }
                    } else if (pushMsgParamVo.getPushObject() == 2) {
                        if (memberUmeng.getDeviceToken().length() == 44) {
                            appMasterSecret = beauticianAndroidProperties.getAppMasterSecret();
                            appKey = beauticianAndroidProperties.getAppKey();
                        } else if (memberUmeng.getDeviceToken().length() == 64) {
                            appKey = beauticianIosProperties.getAppKey();
                            appMasterSecret = beauticianIosProperties.getAppMasterSecret();
                        }
                    } else if (pushMsgParamVo.getPushObject() == 1) {
                        if (memberUmeng.getDeviceToken().length() == 44) {
                            appMasterSecret = consumerAndroidProperties.getAppMasterSecret();
                            appKey = consumerAndroidProperties.getAppKey();
                        } else if (memberUmeng.getDeviceToken().length() == 64) {
                            appMasterSecret = consumerIosProperties.getAppMasterSecret();
                            appKey = consumerIosProperties.getAppKey();
                        }
                    }
                    UmengPushMsg umengPushMsg = new UmengPushMsg();
                    pushHistory = umengPushMsg.pushMsg(pushTemplate, appKey, appMasterSecret, profiles, memberUmeng);
                    if (pushHistory != null) {
//                        this.pushHistoryMapper.insertSelective(pushHistory);
                    }
                }
            } else {
                return new ResponseMessage(30002, "请检查参数的完整性");
            }
        }
        return new ResponseMessage(pushHistory);
    }


    public PushTemplate paramCheck(PushMsgParamVo pushMsgParamVo) {
        //查出模板数据
//        PushTemplate pushTemplate = this.pushTemplateMapper.queryByTemplateCode(pushMsgParamVo.getTemplateCode());
        PushTemplate pushTemplate = new PushTemplate();
        Map<String, String> param = pushMsgParamVo.getParam();
        List<String> paramList = umengProperties.getParamList();
        paramList.forEach(l -> {
            param.forEach((k, v) -> {
                if (l.equals(k)) {
                    pushTemplate.setPushTitle(pushTemplate.getPushTitle().replaceAll(k, v));
                }
            });
        });
        return pushTemplate;
    }
}
