package me.hoonti06.osiv.catalog.query.product;

import java.util.Optional;
import me.hoonti06.osiv.catalog.command.domain.category.CategoryId;
import me.hoonti06.osiv.catalog.command.domain.product.ProductId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface ProductDataDao extends Repository<ProductData, ProductId> {
    Optional<ProductData> findById(ProductId id);

    Page<ProductData> findByCategoryIdsContains(CategoryId id, Pageable pageable);
}
