package tr.itelligence.hackathon.dao;

import tr.itelligence.hackathon.entity.Brand;

import java.util.List;

public interface BrandDAO {
    void save(Brand brand);

    List<Brand> findAll();
}
