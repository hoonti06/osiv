package me.hoonti06.osiv.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.hoonti06.osiv.catalog.query.product.ProductData;
import me.hoonti06.osiv.catalog.query.product.ProductQueryService;
import me.hoonti06.osiv.common.ValidationErrorException;
import me.hoonti06.osiv.member.command.domain.MemberId;
import me.hoonti06.osiv.order.command.application.NoOrderProductException;
import me.hoonti06.osiv.order.command.application.OrderProduct;
import me.hoonti06.osiv.order.command.application.OrderRequest;
import me.hoonti06.osiv.order.command.application.PlaceOrderService;
import me.hoonti06.osiv.order.command.domain.OrderNo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {
  private final ProductQueryService productQueryService;
  private final PlaceOrderService placeOrderService;


  @PostMapping("/orders/order")
  public String order(@ModelAttribute("orderReq") OrderRequest orderRequest,
      BindingResult bindingResult) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    orderRequest.setOrdererMemberId(MemberId.of(user.getUsername()));
    try {
      OrderNo orderNo = placeOrderService.placeOrder(orderRequest);
      return orderNo.getNumber();
    } catch (ValidationErrorException e) {
      e.getErrors().forEach(err -> {
        if (err.hasName()) {
          bindingResult.rejectValue(err.getName(), err.getCode());
        } else {
          bindingResult.reject(err.getCode());
        }
      });
      populateProductsAndTotalAmountsModel(orderRequest);
      return "order/confirm";
    }
  }
  private void populateProductsAndTotalAmountsModel(OrderRequest orderRequest) {
    List<ProductData> products = getProducts(orderRequest.getOrderProducts());
//    modelMap.addAttribute("products", products);
    int totalAmounts = 0;
    for (int i = 0 ; i < orderRequest.getOrderProducts().size() ; i++) {
      OrderProduct op = orderRequest.getOrderProducts().get(i);
      ProductData prod = products.get(i);
      totalAmounts += op.getQuantity() * prod.getPrice().getValue();
    }
//    modelMap.addAttribute("totalAmounts", totalAmounts);
  }

  private List<ProductData> getProducts(List<OrderProduct> orderProducts) {
    List<ProductData> results = new ArrayList<>();
    for (OrderProduct op : orderProducts) {
      Optional<ProductData> productOpt = productQueryService.getProduct(op.getProductId());
      ProductData product = productOpt.orElseThrow(() -> new NoOrderProductException(op.getProductId()));
      results.add(product);
    }
    return results;
  }

}
