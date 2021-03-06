package com.ihason.dtp.easytrans.demos.account.api.constant;

/**
 * 服务常量
 *
 * appId: 应用服务的名称，与 spring.application.name 值相同。用于 RPC 调用时匹配服务。
 *
 * busCode: 请求的标示符，用于注册和查询 请求参数所在的服务接口。
 *
 * @author Hason
 */
public class ServiceConstant {

    public static final String SERVICE_NAME = "account-service";

    public static final String BUS_CODE_DEDUCT = "sagaDeduct";

    /** 扣减余额，补偿模式 */
    public static final String BUS_CODE_CPS_DEDUCT = "compensable-deduct";
    /** 增加积分，补偿模式 */
    public static final String BUS_CODE_CPS_ADD_INTEGRAL = "compensable-add-integral";

}
