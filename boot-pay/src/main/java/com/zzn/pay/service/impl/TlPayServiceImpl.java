package com.zzn.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zzn.common.response.ResponseMessage;
import com.zzn.pay.service.MuppPayService;
import com.zzn.pay.util.HttpConnectionUtil;
import com.zzn.pay.util.SybUtil;
import com.zzn.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author zengzhangni
 * @date 2019/9/9
 */
@Service("TlPayService")
@Slf4j
public class TlPayServiceImpl implements MuppPayService {


    @Override
    public ResponseMessage payH5(H5PayVo vo) throws Exception {
        String appKey = vo.getMerchantConfig().get("appKey");
        StringBuilder sb = new StringBuilder();
        TreeMap<String, String> params = config(vo);

        params.put("reqsn", vo.getOrderNo());
        params.put("trxamt", vo.getAmount());
        params.put("charset", "UTF-8");
        params.put("returl", vo.getReturl());
        params.put("notify_url", vo.getNotifyUrl());
        params.put("body", vo.getBody());
        params.put("remark", vo.getRemark());
        params.put("sign", SybUtil.sign(params, appKey));

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
            }
        }
        String str = vo.getChannelConfig().get("url") + "?" + sb.substring(0, sb.length() - 1);
        return new ResponseMessage<>(str);
    }


    @Override
    public ResponseMessage unifyScanPay(UnifyScanPayVo vo) throws Exception {
        String appKey = vo.getMerchantConfig().get("appKey");
        String url = vo.getChannelConfig().get("url") + "/scanqrpay";
        TreeMap<String, String> params = config(vo);
        params.put("reqsn", vo.getOrderNo());
        params.put("trxamt", vo.getAmount());
        params.put("authcode", vo.getAuthCode());
        params.put("body", vo.getBody());
        params.put("remark", vo.getRemark());

        return new ResponseMessage<>(handleResult(params, url, appKey));

    }


    @Override
    public ResponseMessage cancel(CancelVo vo) throws Exception {
        String appKey = vo.getMerchantConfig().get("appKey");
        String url = vo.getChannelConfig().get("url") + "/cancel";
        TreeMap<String, String> params = config(vo);
        params.put("trxamt", vo.getAmount());
        params.put("oldtrxid", vo.getOldMerchantOrderNo());
        params.put("oldreqsn", vo.getOldOrderNo());
        params.put("reqsn", vo.getRefundNo());
        return new ResponseMessage<>(handleResult(params, url, appKey));
    }


    @Override
    public ResponseMessage refund(RefundVo vo) throws Exception {
        String appKey = vo.getMerchantConfig().get("appKey");
        String url = vo.getChannelConfig().get("url") + "/refund";
        TreeMap<String, String> params = config(vo);
        params.put("trxamt", vo.getAmount());
        params.put("oldreqsn", vo.getOldOrderNo());
        params.put("oldtrxid", vo.getOldMerchantOrderNo());
        params.put("reqsn", vo.getRefundNo());
        params.put("remark", vo.getRemark());
        return new ResponseMessage<>(handleResult(params, url, appKey));
    }


    @Override
    public ResponseMessage query(QueryVo vo) throws Exception {
        String appKey = vo.getMerchantConfig().get("appKey");
        String url = vo.getChannelConfig().get("url") + "/query";
        TreeMap<String, String> params = config(vo);
        params.put("reqsn", vo.getOrderNo());
        params.put("trxid", vo.getMerchantOrderNo());

        return new ResponseMessage<>(handleResult(params, url, appKey));

    }

    @Override
    public Object callBack(String params) throws Exception {

        String[] reqs = params.split("&");
        JSONObject object = new JSONObject();
        for (String param : Arrays.asList(reqs)) {
            String[] arr = param.split("=");
            object.put(arr[0], URLDecoder.decode(arr[1], "utf-8"));
        }


        TreeMap<String, String> map = JSONObject.parseObject(object.toJSONString(), TreeMap.class);
        boolean b = SybUtil.validSign(map, "appKey");
        if (b) {
            if ("0000".equals(map.get("trxstatus"))) {
                System.out.println("支付成功");
            } else {
                System.out.println("支付失败");
            }
            return "SUCCESS";
        } else {
            log.error("签名验证失败!");
        }
        return "FAIL";
    }

    private TreeMap<String, String> config(BaseVo vo) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("appid", vo.getMerchantConfig().get("appid"));
        params.put("cusid", vo.getMerchantConfig().get("cusid"));
        params.put("orgid", vo.getChannelConfig().get("orgid"));
        params.put("randomstr", SybUtil.getValidatecode(8));

        Map<String, String> extend = vo.getExtend();
        if (extend != null && extend.size() > 0) {
            extend.forEach((k, v) -> {
                if (StringUtils.isNotBlank(v)) {
                    params.put(k, v);
                }
            });
        }
        return params;
    }


    private Map<String, String> handleResult(TreeMap<String, String> params, String url, String appKey) throws Exception {

        HttpConnectionUtil http = new HttpConnectionUtil(url);
        http.init();
        params.put("sign", SybUtil.sign(params, appKey));
        byte[] bys = http.postParams(params, true);
        String result = new String(bys, StandardCharsets.UTF_8);

        //log 打印
        responseMsg(result);

        Map<String, String> map = JSONObject.parseObject(result, Map.class);
        if (map == null) {
            throw new Exception("返回数据错误");
        }
        if ("SUCCESS".equals(map.get("retcode"))) {
            TreeMap<String, String> tmap = new TreeMap<>(map);
            if (SybUtil.validSign(tmap, appKey)) {
                return map;
            } else {
                throw new Exception("验证签名失败");
            }
        } else {
            throw new Exception(map.get("retmsg"));
        }
    }

    private void responseMsg(String result) {
        Map map = JSONObject.parseObject(result, Map.class);
        StringBuilder builder = new StringBuilder("\n响应消息:");
        builder.append("\n{");
        map.forEach((k, v) -> {
            builder.append("\n  \"" + k + "\":\"" + v + "\",");
        });
        builder.append("\n}");
        log.info(builder.toString());
    }
}
