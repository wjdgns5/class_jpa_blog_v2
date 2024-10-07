package com.tenco.blog_v1.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository // Ioc
public class BoardNativeRepository {
    // DI 처리
    private final EntityManager em;

    /**
     * 새로운 게시글 생성
     * @param title
     * @param content
     */
    @Transactional
    public void save(String title, String content) {
        Query query = em.createNativeQuery(
                "INSERT INTO board_tb(title, content, created_at) VALUES (?, ?, NOW())"
        );

        query.setParameter(1, title);
        query.setParameter(2, content);
        // 실행
        query.executeUpdate();
    }

    /**
     * 특정 ID의 게시글을 조회 합니다.
     * @param id
     * @return
     */
    public Board findById(int id) {
        Query query = em.createNativeQuery("SELECT * FROM board_tb WHERE id = ?", Board.class);
        query.setParameter(1, id);
        return (Board)query.getSingleResult(); // 단일행으로 나올 때
        // query.getSingleResult() 이 Object로 나왔고 받는 타입은 Board 이므로 다운캐스팅 사용
    }

    /**
     * 모든 게시글 조회
     * @return
     */
    public List<Board> findAll() {
       Query query = em.createNativeQuery("SELECT * FROM board_tb ORDER By id DESC", Board.class);
        return query.getResultList();
    }

    /**
     * 특정 ID로 게시글을 수정하는 기능
     * @param id
     * @param title
     * @param content
     */

    @Transactional
    public void updateById(int id, String title, String content) {
        Query query = em.createNativeQuery("UPDATE board_tb SET title = ?, content = ? WHERE id = ?");
        // 위치 기반으로 파라미터 세팅하고 있다.
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);

        // 쿼리 실행
        query.executeUpdate();
    }

    /**
     * 특정 ID의 게시글을 삭제 합니다.
     * @param id
     */
    public void deleteById(int id) {
        Query query =
            em.createNativeQuery("DELETE FROM board_tb WHERE id = ?");
        query.setParameter(1,id);
        query.executeUpdate();

    }

}
