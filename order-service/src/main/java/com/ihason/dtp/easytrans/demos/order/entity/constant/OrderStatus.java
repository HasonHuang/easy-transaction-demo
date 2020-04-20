package com.ihason.dtp.easytrans.demos.order.entity.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 订单状态
 *
 * @author Hason
 */
public enum OrderStatus implements IEnum<String> {

    PENDING_CREATE,
    CREATING,
    SUCCESS,
    FAIL,
    ;


    @Override
    public String getValue() {
        return name();
    }
}
