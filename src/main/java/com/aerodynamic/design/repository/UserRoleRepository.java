package com.aerodynamic.design.repository;

import org.springframework.data.repository.CrudRepository;

import com.aerodynamic.design.domain.admin.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole,Long>{

}
