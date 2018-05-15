package com.dawhey.retailcart.repositories;

import com.dawhey.retailcart.entities.PurchaseAction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "purchases", path = "purchases")
public interface PurchaseActionRepository extends PagingAndSortingRepository<PurchaseAction, Long> {
}
