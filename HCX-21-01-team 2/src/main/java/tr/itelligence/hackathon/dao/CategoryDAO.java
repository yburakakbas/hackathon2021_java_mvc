package tr.itelligence.hackathon.dao;

import tr.itelligence.hackathon.entity.Category;

import java.util.List;

public interface CategoryDAO {
    void save(Category category);
    List<Category> findAll();
}
