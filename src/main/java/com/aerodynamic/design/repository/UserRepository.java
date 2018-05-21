package com.aerodynamic.design.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.aerodynamic.design.domain.admin.Role;
import com.aerodynamic.design.domain.admin.User;

public interface UserRepository extends CrudRepository<User,Long>{
	
	public User findByName(String name);
	
	@Query("select user from User user,UserRole userRole where userRole.user=user and userRole.role=:role")
	public Iterable<User> findUsersByRole(@Param("role")Role role);
	
	@Query("select role from Role role,UserRole userRole where userRole.role=role and userRole.user=:user")
	public Iterable<Role> findRolesByUser(@Param("user")User user);
}
