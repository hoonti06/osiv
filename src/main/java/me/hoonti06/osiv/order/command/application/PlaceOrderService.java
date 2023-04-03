package me.hoonti06.osiv.order.command.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.hoonti06.osiv.catalog.command.domain.product.Product;
import me.hoonti06.osiv.catalog.command.domain.product.ProductId;
import me.hoonti06.osiv.catalog.query.product.ProductRepository;
import me.hoonti06.osiv.common.ValidationError;
import me.hoonti06.osiv.common.ValidationErrorException;
import me.hoonti06.osiv.order.command.domain.Order;
import me.hoonti06.osiv.order.command.domain.OrderLine;
import me.hoonti06.osiv.order.command.domain.OrderNo;
import me.hoonti06.osiv.order.command.domain.OrderRepository;
import me.hoonti06.osiv.order.command.domain.OrderState;
import me.hoonti06.osiv.order.command.domain.Orderer;
import me.hoonti06.osiv.order.command.domain.OrdererService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaceOrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrdererService ordererService;

    public PlaceOrderService(ProductRepository productRepository,
                             OrderRepository orderRepository,
                             OrdererService ordererService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.ordererService = ordererService;
    }

    @Transactional
    public OrderNo placeOrder(OrderRequest orderRequest) {
        List<ValidationError> errors = validateOrderRequest(orderRequest);
        if (!errors.isEmpty()) throw new ValidationErrorException(errors);

        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderProduct op : orderRequest.getOrderProducts()) {
            Optional<Product> productOpt = productRepository.findById(new ProductId(op.getProductId()));
            Product product = productOpt.orElseThrow(() -> new NoOrderProductException(op.getProductId()));
            orderLines.add(new OrderLine(product.getId(), product.getPrice(), op.getQuantity()));
        }
        OrderNo orderNo = orderRepository.nextOrderNo();
        Orderer orderer = ordererService.createOrderer(orderRequest.getOrdererMemberId());

        Order order = new Order(orderNo, orderer, orderLines, orderRequest.getShippingInfo(), OrderState.PAYMENT_WAITING);
        orderRepository.save(order);
        return orderNo;
    }

    private List<ValidationError> validateOrderRequest(OrderRequest orderRequest) {
        return new OrderRequestValidator().validate(orderRequest);
    }

}
