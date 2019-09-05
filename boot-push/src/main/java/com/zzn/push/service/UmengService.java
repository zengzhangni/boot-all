package com.zzn.push.service;

import com.zzn.push.config.ResponseMessage;
import com.zzn.push.model.PushMsgParamVo;

import java.util.List;

/**
 * @author zengzhangni
 * @date 2019/3/14
 */
public interface UmengService {


    ResponseMessage pushMessage(List<PushMsgParamVo> pushMsgParamVoList);
}
