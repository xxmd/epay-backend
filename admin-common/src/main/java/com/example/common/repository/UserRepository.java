package com.example.common.repository;

import com.example.common.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("select distinct u from User u " +
            "left join fetch u.roleSet r " +
            "left join fetch r.menuSet " +
            "where u.id = :id")
    Optional<User> findByIdWithRolesAndMenus(@Param("id") Long id);
}
