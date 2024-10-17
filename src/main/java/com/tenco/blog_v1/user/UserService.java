package com.tenco.blog_v1.user;

import com.tenco.blog_v1.common.errors.Exception400;
import com.tenco.blog_v1.common.errors.Exception401;
import com.tenco.blog_v1.common.errors.Exception404;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    // @Autowired
    private final UserJPARepository userJPARepository;

    /**
     * 회원 가입 서비스
     */
    @Transactional
    public void signUp(UserDTO.JoinDTO reqDto ) {
        // 1. username <-- 유니크 확인
        Optional<User> userOp = userJPARepository.findByUsername(reqDto.getUsername());
        if(userOp.isPresent()) {
            throw new Exception400("중복된 유저네임입니다");
        }
        // 회원 가입
        userJPARepository.save(reqDto.toEntity());
    }

    /**
     *  로그인 서비스
     *
     */
    public User signIn(UserDTO.LoginDTO reqDTO) {
        User seessionUser = userJPARepository
                .findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword())
                .orElseThrow( () -> new Exception401("인증되지 않았습니다"));
        return seessionUser;
    }

    /**
     * 회원 정보 조회 서비스
     *
     * @param id 조회할 사용자 ID
     * @return 조회된 사용자 객체
     * @throws Exception404 사용자를 찾을 수 없는 경우 발생
     */
    public User readUser(int id){
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다"));
        return user;
    }
    /**
     * 회원 정보 수정 서비스
     *
     * @param id 수정할 사용자 ID
     * @param reqDTO 수정된 사용자 정보 DTO
     * @return 수정된 사용자 객체
     * @throws Exception404 사용자를 찾을 수 없는 경우 발생
     */
    @Transactional // 트랜잭션 관리
    public User updateUser(int id, UserDTO.UpdateDTO reqDTO){
        // 1. 사용자 조회 및 예외 처리
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다"));

        // 2. 사용자 정보 수정
        user.setPassword(reqDTO.getPassword());
        user.setEmail(reqDTO.getEmail());

        // 더티 체킹을 통해 변경 사항이 자동으로 반영됩니다.
        return user;
    }
}
