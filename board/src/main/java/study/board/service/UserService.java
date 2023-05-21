package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.User;
import study.board.exception.EmailDupException;
import study.board.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public String join(User user) {

        //email 중복 check
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new EmailDupException(user.getEmail() + "이 이미 존재합니다.");
                });

        //저장
        userRepository.save(user);


        return "SUCCESS";
    }
}
