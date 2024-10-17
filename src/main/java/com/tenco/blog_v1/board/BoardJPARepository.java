package com.tenco.blog_v1.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// @Repository 생략가능
public interface BoardJPARepository extends JpaRepository<Board, Integer>{

    // 커스텀 쿼리 만들어 보기
    // Board 와 User 엔티티를 조인하여 특정 Board 엔티티를 조회한다.
    @Query("select b from Board b join fetch b.user u where b.id = :id")
    Optional<Board> findByIdJoinUser(@Param("id") int id);

}
