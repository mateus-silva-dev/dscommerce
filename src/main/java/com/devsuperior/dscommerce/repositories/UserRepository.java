package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = """
            SELECT u.email AS username, u.password, r.id AS roleId, r.authority
            FROM tb_user u
            INNER JOIN tb_user_role ur ON u.id = ur.user_id
            INNER JOIN tb_role r ON r.id = ur.role_id
            WHERE u.email = :email
    """)
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);

}
