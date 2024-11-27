package cn.houtaroy.authorization.server.userdetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 用户Repository类
 *
 * @author Houtaroy
 */
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
