/**
 * 
 */
package com.ig.ecommsolution.api.gateway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ig.ecommsolution.api.gateway.domain.Role;

/**
 * @author Yashpal.Singh
 *
 */
public interface RoleRepository extends MongoRepository<Role, String> {

}
