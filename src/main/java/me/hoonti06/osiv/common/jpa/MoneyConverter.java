package me.hoonti06.osiv.common.jpa;

import javax.persistence.AttributeConverter;
import me.hoonti06.osiv.common.model.Money;

public class MoneyConverter implements AttributeConverter<Money, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Money money) {
    return money == null ? null : money.getValue();
  }

  @Override
  public Money convertToEntityAttribute(Integer value) {
    return value == null ? null : new Money(value);
  }
}
