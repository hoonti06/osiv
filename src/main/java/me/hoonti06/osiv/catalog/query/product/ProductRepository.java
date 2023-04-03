package me.hoonti06.osiv.catalog.query.product;

import java.util.Optional;
import me.hoonti06.osiv.catalog.command.domain.product.Product;
import me.hoonti06.osiv.catalog.command.domain.product.ProductId;
import org.springframework.data.repository.Repository;

public interface ProductRepository extends Repository<Product, ProductId> {
    void save(Product product);

    Optional<Product> findById(ProductId id);

    void flush();
}

