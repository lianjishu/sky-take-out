package io.github.lianjishu.service;

import io.github.lianjishu.dto.OrdersPaymentDTO;
import io.github.lianjishu.dto.OrdersSubmitDTO;
import io.github.lianjishu.entity.Orders;
import io.github.lianjishu.vo.OrderPaymentVO;
import io.github.lianjishu.vo.OrderSubmitVO;

public interface OrderService {
    Orders list();

    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);
}
