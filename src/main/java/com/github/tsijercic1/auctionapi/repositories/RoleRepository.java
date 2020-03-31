package com.github.tsijercic1.auctionapi.repositories;

import com.github.tsijercic1.auctionapi.models.Role;
import com.github.tsijercic1.auctionapi.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * interface used to access the database
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName roleName);
}
