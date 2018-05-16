package com.dawhey.retailcart.repositories;

import com.dawhey.retailcart.entities.CartUser;
import com.dawhey.retailcart.entities.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "carts", path = "carts")
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

   ShoppingCart findFirstById(Long id);

   ShoppingCart findFirstByCurrentUser(CartUser user);
}
