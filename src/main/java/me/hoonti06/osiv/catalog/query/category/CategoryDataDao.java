package me.hoonti06.osiv.catalog.query.category;

import java.util.List;
import java.util.Optional;
import me.hoonti06.osiv.catalog.command.domain.category.CategoryId;
import org.springframework.data.repository.Repository;

public interface CategoryDataDao extends Repository<CategoryData, CategoryId> {
    Optional<CategoryData> findById(CategoryId id);

    List<CategoryData> findAll();
}
