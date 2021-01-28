package tr.itelligence.hackathon.dao;

import tr.itelligence.hackathon.entity.Subcategory;

import java.util.List;

public interface SubcategoryDAO {
    void save(Subcategory subcategory);
    List<Subcategory> findAll();
}
