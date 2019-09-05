package com.zzn.push.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;


/**
 * @author zengzhangni
 * @date 2019/3/15
 */
@Data
@EqualsAndHashCode
@ApiModel(value="推送消息所需参数Vo")
public class PushMsgParamVo {

    @ApiModelProperty("推送消息模板Code(必填)")
    private String templateCode;

    @ApiModelProperty("推送会员memberUuId,必填")
    private String memberUuid;

    @ApiModelProperty("订单编号(当推送Code为 SERVICE{_013,_014}必填)")
    private String orderNo;

    @ApiModelProperty("邦女郎姓名(当推送Code为:PROJECT{_001_002,_003},SYSTEM{_004,_005,_006,_007,_015,_016,_019,_020}必填")
    private String beauticianName;

    @ApiModelProperty("课程名字,(推送的Code为SYSTEM_012时，必填)")
    private String courseName;

    @ApiModelProperty("课程开始时间,(推送的Code为SYSTEM_012时，必填)")
    private String courseTime;

    @ApiModelProperty("活动名字,当推送code为(SYSTEM{_013,_014})必填")
    private String activityName;

    @ApiModelProperty("活动开始时间yyyy-MM-dd hh:mm:ss,当推送code为(SYSTEM{_013,_014})必填")
    private String activityTime;

    @ApiModelProperty("推送内容参数(必填)")
    private Map<String,Object> customs;

    @ApiModelProperty("跳转类型(系统消息招募挂靠跳转参数)")
    private Integer target;

    @ApiModelProperty("参数(系统消息招募挂靠内容参数)")
    private Map<String,String> param;

    @ApiModelProperty("推送类型 1:用户 2:邦女郎 3:店长")
    private Integer PushObject;




    /**
     * 一：推送服务消息
     *      1.推送内容参数统一格式{type:ORDER_TYPE,id:你的订单id}
     *
     * 二：推送项目消息
     *      1.当推送的项目消息Code值为(PROJECT_001,PROJECT_0012,PROJECT_003,PROJECT_004,PROJECT_005,PROJECT_006)时，
     *       推送的内容参数格式{type:PRODUCTSTOREREFID_TYPE,id:你的邦女郎-门店项目操作id}
     *     2.推送项目消息的Code值为(PROJECT_007),
     *       推送的内容参数格式{"type":COURSE_TYPE,"id":你的课程id}
     *
     * 三：推送系统消息
     *      1.推送Code为(SYSTEM_001),推送内容格式:{"type",MAKE_MONEY_BEAUTICIAN_TYPE,"id":邦女郎的id}
     *      2.推送Code为(SYSTEM_002),推送内容格式：{"type",MAKE_MONEY_STORE_TYPE,"id":你的门店id,"date":"值（201805,201806）"}
     *      3.推送Code为(SYSTEM_003至SYSTEM_011,推送内容格式：{"type":STORE_BEAUTICIAN_REF，"id":门店邦女郎的RefId})
     *      4.推送Code为(SYSTEM_012),推送内容格式：{"type",COURSE_TYPE,"id":课程id}
     *      5.推送Code为(SYSTEM_013,SYSTEM_014),推送内容格式：{"type",ACTIVITY_TYPE,"id":你的活动id}
     */

}
