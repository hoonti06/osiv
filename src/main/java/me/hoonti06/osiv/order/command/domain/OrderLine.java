package me.hoonti06.osiv.order.command.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import me.hoonti06.osiv.catalog.command.domain.product.ProductId;
import me.hoonti06.osiv.common.jpa.MoneyConverter;
import me.hoonti06.osiv.common.model.Money;

@Embeddable
public class OrderLine {
  @Embedded
  private ProductId productId;

  @Convert(converter = MoneyConverter.class)
  @Column(name = "price")
  private Money price;

  @Column(name = "quantity")
  private int quantity;

  @Convert(converter = MoneyConverter.class)
  @Column(name = "amounts")
  private Money amounts;

  protected OrderLine() {
  }

  public OrderLine(ProductId productId, Money price, int quantity) {
    this.productId = productId;
    this.price = price;
    this.quantity = quantity;
    this.amounts = calculateAmounts();
  }

  private Money calculateAmounts() {
    return price.multiply(quantity);
  }

  public ProductId getProductId() {
    return productId;
  }

  public Money getPrice() {
    return price;
  }

  public int getQuantity() {
    return quantity;
  }

  public Money getAmounts() {
    return amounts;
  }
}

