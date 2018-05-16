package com.dawhey.retailcart.repositories;

import com.dawhey.retailcart.entities.PurchaseAction;
import com.dawhey.retailcart.entities.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "purchases", path = "purchases")
public interface PurchaseActionRepository extends CrudRepository<PurchaseAction, Long> {

    PurchaseAction findFirstByCart(ShoppingCart cart);
}
