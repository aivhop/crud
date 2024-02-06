package com.aivhop.crud.crudapp.repositories;

import com.aivhop.crud.crudapp.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {
}
