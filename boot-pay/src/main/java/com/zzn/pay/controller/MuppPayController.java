package com.zzn.pay.controller;


import com.zzn.common.bean.BeanFactoryUtil;
import com.zzn.common.response.ResponseMessage;
import com.zzn.pay.enums.PayChannelType;
import com.zzn.pay.service.MuppPayService;
import com.zzn.pay.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengzhangni
 * @date 2019/9/9
 */
@Api(tags = "支付统一入口")
@RestController
@RequestMapping("mupp")
public class MuppPayController {


    @ApiOperation("h5支付(用户扫码)")
    @PostMapping("/h5Pay")
    public ResponseMessage h5Pay(@RequestBody @Validated H5PayVo vo) throws Exception {
        return getService(vo).payH5(vo);
    }

    @ApiOperation("统一扫码（商户主扫）")
    @PostMapping("/unifyScanPay")
    public ResponseMessage unifyScanPay(@RequestBody @Validated UnifyScanPayVo vo) throws Exception {
        return getService(vo).unifyScanPay(vo);
    }

    @ApiOperation("统一退款")
    @PostMapping("/refund")
    public ResponseMessage refund(@RequestBody @Validated RefundVo vo) throws Exception {
        return getService(vo).refund(vo);
    }

    @ApiOperation("统一查询")
    @PostMapping("/unifyQuery")
    public ResponseMessage query(@RequestBody @Validated QueryVo vo) throws Exception {
        return getService(vo).query(vo);
    }

    @ApiOperation("统一撤销")
    @PostMapping("/unifyCancel")
    public ResponseMessage cancel(@RequestBody @Validated CancelVo vo) throws Exception {
        return getService(vo).cancel(vo);
    }

    @ApiOperation("回调")
    @PostMapping("/notify/{type}")
    public ResponseMessage notify(@RequestBody String params, @PathVariable(value = "type") String type) throws Exception {
        PayChannelType payChannelType = PayChannelType.valueOf(type);
        BaseVo baseVo = new BaseVo();
        baseVo.setChannelId(payChannelType.getTargetClass());
        return new ResponseMessage<>(getService(baseVo).callBack(params));
    }

    private MuppPayService getService(BaseVo vo) {
        return (MuppPayService) BeanFactoryUtil.getBeanByName(vo.getChannelId());
    }
}
