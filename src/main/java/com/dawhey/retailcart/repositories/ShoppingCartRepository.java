package com.dawhey.retailcart.repositories;

import com.dawhey.retailcart.entities.ShoppingCart;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "carts", path = "carts")
public interface ShoppingCartRepository extends PagingAndSortingRepository<ShoppingCart, Long> {
}
