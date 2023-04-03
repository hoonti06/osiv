package me.hoonti06.osiv.order.command.application;

import java.util.List;
import me.hoonti06.osiv.member.command.domain.MemberId;
import me.hoonti06.osiv.order.command.domain.ShippingInfo;

public class OrderRequest {
    private List<OrderProduct> orderProducts;
    private MemberId ordererMemberId;
    private ShippingInfo shippingInfo;

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public MemberId getOrdererMemberId() {
        return ordererMemberId;
    }

    public void setOrdererMemberId(MemberId ordererMemberId) {
        this.ordererMemberId = ordererMemberId;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }
}
