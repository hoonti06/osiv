package me.hoonti06.osiv.order.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderLine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private final Long id;

  private final Long productId;
  private final int quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_number"))
  @OrderColumn(name = "line_idx")
  private final Order order;
}


