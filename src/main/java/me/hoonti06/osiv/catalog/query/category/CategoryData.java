package me.hoonti06.osiv.catalog.query.category;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import me.hoonti06.osiv.catalog.command.domain.category.CategoryId;

@Entity
@Table(name = "category")
public class CategoryData {
    @EmbeddedId
    private CategoryId id;

    @Column(name = "name")
    private String name;

    protected CategoryData() {
    }

    public CategoryData(CategoryId id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryId getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
