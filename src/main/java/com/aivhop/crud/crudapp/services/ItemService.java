package com.aivhop.crud.crudapp.services;

import com.aivhop.crud.crudapp.models.Item;
import com.aivhop.crud.crudapp.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class ItemService {
    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> findAll(){
        return itemsRepository.findAll();
    }

    //todo another methods

}
