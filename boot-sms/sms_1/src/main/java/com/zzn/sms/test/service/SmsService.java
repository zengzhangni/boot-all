package com.zzn.sms.test.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;
import com.zzn.common.response.ResponseMessage;
import com.zzn.sms.test.message.SmsMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zengzhangni
 * @date 2019/5/9
 */
@Component
public class SmsService {

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    static final String PRODUCT = "Dysmsapi";
    /**
     * 产品域名,开发者无需替换
     */
    static final String DOMAIN = "dysmsapi.aliyuncs.com";

    @Value("${ali.sms.accessKeyId}")
    String accessKeyId;
    @Value("${ali.sms.accessKeySecret}")
    String accessKeySecret;
    @Value("${ali.sms.signName}")
    String signName;
    @Value("${ali.available}")
    private boolean smsAvailable = false;

    /**
     * 发送短信
     *
     * @param smsMessage
     * @return
     * @throws ClientException
     */
    public ResponseMessage<String> sendSmsMessage(SmsMessage smsMessage) throws ClientException {
        IAcsClient acsClient = init();

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = getSendSmsRequest(smsMessage.getPhone(), smsMessage.getTemplateCode());
        Map<String, Object> smsContent = smsMessage.getSmsContent();
        String toJson = new Gson().toJson(smsContent);
        request.setTemplateParam(toJson);

        request.setOutId("yourOutId");
        //调用阿里云发送短信接口, 此处可能会抛出异常，注意catch
        String send = isSend(acsClient, request);
        //保存发送历史
        return new ResponseMessage<>(send);
    }


    /**
     * 初始化
     */
    private IAcsClient init() throws ClientException {
        //1.初始化
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        return new DefaultAcsClient(profile);
    }


    /**
     * 是否发送
     */
    private String isSend(IAcsClient acsClient, SendSmsRequest request) throws ClientException {
        if (smsAvailable) {
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            String requestId = sendSmsResponse.getRequestId();
            String code = sendSmsResponse.getCode();
            String message = sendSmsResponse.getMessage();
            String bizId = sendSmsResponse.getBizId();
            return sendSmsResponse.getMessage();
        }
        return null;
    }

    /**
     * 获取请求
     *
     * @return
     */
    private SendSmsRequest getSendSmsRequest(String phone, String templateCode) {
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        return request;
    }


}
