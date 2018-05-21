package com.aerodynamic.design.repository;

import org.springframework.data.repository.CrudRepository;

import com.aerodynamic.design.domain.admin.Role;

public interface RoleRepository extends CrudRepository<Role,Long>{

}
