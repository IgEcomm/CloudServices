/**
 * 
 */
package com.ig.ecommsolution.api.gateway.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ig.ecommsolution.api.gateway.domain.UserRole;

/**
 * @author Yashpal.Singh
 *
 */
public interface UserRoleRepository extends MongoRepository<UserRole, String>{

	public List<UserRole> findByUserId(String userId);
	
}
