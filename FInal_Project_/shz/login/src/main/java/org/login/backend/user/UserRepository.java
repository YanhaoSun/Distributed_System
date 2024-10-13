package org.login.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //    @Query("from User ")
    User findByUserNameAndPassword(String userName, String password);
    User findUserByUserId(int userid);
    User findUserByUserName(String userName);
    Integer findAgeByUserId(int userId);

    boolean existsUserByUserName(String username);

    boolean existsUserByUserNameAndPassword(String username, String passwd);
}