package com.zzn.sms.test.service;

import com.alibaba.fastjson.JSONObject;
import com.zzn.common.encryption.SHA1;
import com.zzn.common.http.HttpClientUtil;
import com.zzn.common.redis.RedisService;
import com.zzn.common.response.ResponseMessage;
import com.zzn.sms.test.message.WxSendVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zengzhangni
 * @date 2019/3/26
 */
@Service
@Slf4j
public class WxSendService {


    @Resource
    private RedisService redisService;


    public ResponseMessage<Map> toObtainPosition(String platformCode, String url) {
        String token = getToken(platformCode);
        String uri = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
        String resultEntity;
        Map resultMap;
        String ticket = null;
        try {
//            resultEntity = HttpClientUtil.doGet(uri);
            resultEntity = "";
            resultMap = JSONObject.parseObject(resultEntity, Map.class);
            log.info("获取位置响应实体:{}", resultMap);
            ticket = resultMap.get("ticket").toString();
        } catch (Exception e) {
//            throw new MemberException("获取位置异常!", e.getStackTrace()[0]);
        }
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        long timestamp = System.currentTimeMillis();

        String str = String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s", ticket, nonceStr, timestamp, url);

        String signature = SHA1.encode(str);
        Map<String, Object> map = new HashMap<>(4);
        map.put("appId", "");
        map.put("timestamp", timestamp);
        map.put("nonceStr", nonceStr);
        map.put("signature", signature);

        return new ResponseMessage<>(map);
    }

    public ResponseMessage<Map> sendWxMsg(WxSendVo wxSendVo) {
        String openId = wxSendVo.getOpenId();
        Object[] args = wxSendVo.getArgs();
        String memberUuid = wxSendVo.getMemberUuid();
        String pTemplateCode = wxSendVo.getPTemplateCode();
        String url = wxSendVo.getUrl();
        Map miniProgram = wxSendVo.getMiniProgram();
        String color = wxSendVo.getColor();
        if (StringUtils.isBlank(openId)) {
            openId = getOpenId(memberUuid);
        }
//        WxTemplate wxTemplate = this.wxTemplateMapper.queryByPTemplateCode(pTemplateCode);
//        String platformCode = wxTemplate.getPlatformCode();
//        String description = wxTemplate.getTemplateDescription();
        String platformCode = "";
        String description = "";
        String format = String.format(description, args);
        Map<String, Object> data = JSONObject.parseObject(format);
        return sendMessage(data, platformCode, memberUuid, openId, "", url, miniProgram, color);
    }

    /**
     * 发送消息
     */
    private ResponseMessage<Map> sendMessage(Map<String, Object> data, String platformCode, String memberUuid, String openId, String templateCode, String url, Map miniProgram, String color) {
        String token = getToken(platformCode);
        String uri = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
        // 装配post请求参数
        JSONObject json = new JSONObject();
        json.put("touser", openId);
        json.put("template_id", templateCode);
        if (StringUtils.isNotBlank(url)) {
            json.put("url", url);
        }
        if (miniProgram != null) {
            json.put("miniprogram", miniProgram);
        }
        JSONObject dataJson = new JSONObject();
        data.forEach((k, v) -> {
            JSONObject sonDateJson = new JSONObject();
            sonDateJson.put("value", v);
            sonDateJson.put("color", color != null ? color : "#2030A0");
            dataJson.put(k, sonDateJson);
        });
        json.put("data", dataJson);
        StringEntity myEntity = new StringEntity(json.toJSONString(), ContentType.APPLICATION_JSON);
        log.info("模板消息请求参数:{}", json);
        Map map = null;
        try {
//            String resultEntity = HttpClientUtil.doPost(uri, myEntity);
            String resultEntity = "";
            // 发送成功
            map = JSONObject.parseObject(resultEntity, Map.class);
            log.info("发送模板消息响应实体:{}", map);
            int errCode = (int) map.get("errcode");
            if (errCode == 0) {
                //保存发送历史
            }
        } catch (Exception e) {
            log.error("微信模板消息发送异常!:{}", e.getStackTrace()[0]);
        }
        return new ResponseMessage<>(map);
    }

    /**
     * 获取openId
     */
    private String getOpenId(String memberUuid) {
//        return memberWxMapper.queryByMemberUuid(memberUuid).getOpenId();
        return "";
    }

    /**
     * 根据微信id，secret获取access_token
     */
    private String getAccessToken(String appId, String appSecret) {
        String wxAccessToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        String tmpUrl = String.format(wxAccessToken, appId, appSecret);
        Map map;
        try {
            String entity = HttpClientUtil.doGet(tmpUrl);
            map = JSONObject.parseObject(entity, Map.class);
            log.info("获取access_token响应实体:{}", map);
            if (map.get("access_token") != null) {
                return map.get("access_token").toString();
            } else {
            }
        } catch (Exception e) {
            log.error("获取微信token异常!:{}", e.getStackTrace()[0]);
        }
        return "";
    }

    /**
     * 获取Token
     */
    private String getToken(String platformCode) {
        String token = redisService.getStr(platformCode);
        log.info("当前token为:{}", token);
        if (StringUtils.isBlank(token)) {
            //重新获取token
            token = this.getAccessToken("AppId", "AppSecret");
            //重新放入缓存2小时
            this.redisService.setStr(platformCode, token, 7200L);
            log.info("重新获取token为:{}", token);
        }
        return token;
    }

}
