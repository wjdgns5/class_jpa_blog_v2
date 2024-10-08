package com.tenco.blog_v1.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    /**
     * 사용자 이름과 비밀번호로 사용자 조회
     * @param username
     * @param password
     * @return 조회된 User 엔티티, Null
     */
    public User findByUserNameAndPassword(String username, String password) {
        TypedQuery<User> jpql =
                em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
        jpql.setParameter("username", username);
        jpql.setParameter("password", password);

        return jpql.getSingleResult();
    }

}
