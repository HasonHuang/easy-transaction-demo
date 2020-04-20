package com.ihason.dtp.easytrans.demos.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ihason.dtp.easytrans.demos.order.entity.constant.OrderStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 订单实体
 *
 * @author Hason
 */
@Data
@Accessors(chain = true)
@TableName("`order`")
public class Order {

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("order_number")
    private String orderNumber;
    @TableField("user_id")
    private Long userId;
    private BigDecimal amount;
    private OrderStatus status;
    @TableField("created_time")
    private LocalDateTime createdTime;
    @TableField("updated_time")
    private LocalDateTime updatedTime;

    public static Order create(Long userId, BigDecimal amount) {
        return new Order()
                .setUserId(userId)
                .setOrderNumber(UUID.randomUUID().toString())
                .setAmount(amount)
                .setStatus(OrderStatus.PENDING_CREATE)
                .setCreatedTime(LocalDateTime.now())
                .setUpdatedTime(LocalDateTime.now());
    }
}