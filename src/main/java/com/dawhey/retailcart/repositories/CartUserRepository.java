package com.dawhey.retailcart.repositories;

import com.dawhey.retailcart.entities.CartUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface CartUserRepository extends CrudRepository<CartUser, Long> {

    CartUser findOneByUserName(String userName);

    CartUser findOneByToken(String token);
}
