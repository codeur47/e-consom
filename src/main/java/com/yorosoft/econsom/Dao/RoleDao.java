package com.yorosoft.econsom.Dao;

import com.yorosoft.econsom.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
