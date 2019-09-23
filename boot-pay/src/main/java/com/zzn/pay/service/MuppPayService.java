package com.zzn.pay.service;


import com.zzn.common.response.ResponseMessage;
import com.zzn.pay.vo.*;

/**
 * @author zengzhangni
 * @date 2019/9/9
 */
public interface MuppPayService {
    /**
     * H5 用户扫码
     *
     * @param vo
     * @return
     * @throws Exception
     */
    ResponseMessage payH5(H5PayVo vo) throws Exception;

    /**
     * 商户扫码
     *
     * @param unifyScanPayVo
     * @return
     * @throws Exception
     */
    ResponseMessage unifyScanPay(UnifyScanPayVo unifyScanPayVo) throws Exception;


    /**
     * 统一退款
     *
     * @param vo
     * @return
     * @throws Exception
     */
    ResponseMessage refund(RefundVo vo) throws Exception;

    /**
     * 统一查询交易
     *
     * @param queryVo
     * @return
     * @throws Exception
     */
    ResponseMessage query(QueryVo queryVo) throws Exception;

    /**
     * 统一撤销
     *
     * @param cancelVo
     * @return
     * @throws Exception
     */
    ResponseMessage cancel(CancelVo cancelVo) throws Exception;


    /**
     * 支付回调
     *
     * @param params
     * @return
     */
    Object callBack(String params) throws Exception;
}
