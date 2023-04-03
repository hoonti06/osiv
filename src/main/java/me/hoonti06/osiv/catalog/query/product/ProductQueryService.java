package me.hoonti06.osiv.catalog.query.product;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import me.hoonti06.osiv.catalog.NoCategoryException;
import me.hoonti06.osiv.catalog.command.domain.category.CategoryId;
import me.hoonti06.osiv.catalog.command.domain.product.ProductId;
import me.hoonti06.osiv.catalog.query.category.CategoryData;
import me.hoonti06.osiv.catalog.query.category.CategoryDataDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductQueryService {
    private ProductDataDao productDataDao;
    private CategoryDataDao categoryDataDao;

    public ProductQueryService(ProductDataDao productDataDao,
                               CategoryDataDao categoryDataDao) {
        this.productDataDao = productDataDao;
        this.categoryDataDao = categoryDataDao;
    }

    @Transactional
    public CategoryProduct getProductInCategory(Long categoryId, int page, int size) {
        CategoryData category = categoryDataDao.findById(new CategoryId(categoryId))
                .orElseThrow(NoCategoryException::new);

        Page<ProductData> productPage = productDataDao.findByCategoryIdsContains(category.getId(), Pageable.ofSize(size).withPage(page - 1));
        return new CategoryProduct(category,
                toSummary(productPage.getContent()),
                page,
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages());
    }

    private List<ProductSummary> toSummary(List<ProductData> products) {
        return products.stream().map(
                prod -> new ProductSummary(
                        prod.getId().getId(),
                        prod.getName(),
                        prod.getPrice().getValue(),
                        prod.getFirstImageThumbnailPath())).collect(toList());
    }

    public Optional<ProductData> getProduct(String productId) {
        return productDataDao.findById(new ProductId(productId));
    }
}
