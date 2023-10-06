package com.ceos18.springboot.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ceos18.springboot.user.entity.User;
import org.springframework.test.context.junit4.SpringRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserRepository() {

        // given
        User myMelody = User.builder()
                .nickname("마이멜로디")
                .email("mymelody@gmail.com")
                .phone("010-1234-5678")
                .password("mamerie0v0")
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .temperature(BigDecimal.valueOf(36.7))
                .updated(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        User kuromi = User.builder()
                .nickname("쿠로미")
                .email("kurooooo@gmail.com")
                .phone("010-3434-3434")
                .password("kuromzzang!")
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .temperature(BigDecimal.valueOf(40))
                .updated(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        User kitty = User.builder()
                .nickname("키티")
                .email("kitty@gmail.com")
                .phone("010-1111-3434")
                .password("kitty!0!")
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .temperature(BigDecimal.valueOf(40))
                .updated(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        // when
        userRepository.save(myMelody);
        userRepository.save(kuromi);
        userRepository.save(kitty);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(3);

        // 닉네임이 "쿠로미"인 사용자 찾기
        Optional<User> foundUser1 = userRepository.findByNickname("쿠로미");
        assertTrue(foundUser1.isPresent());
        assertEquals("쿠로미", foundUser1.get().getNickname());

        // 이메일이 "mymelody@gmail.com"인 사용자 찾기
        Optional<User> foundUser2 = userRepository.findByEmail("mymelody@gmail.com");
        assertTrue(foundUser2.isPresent());
        assertEquals("mymelody@gmail.com", foundUser2.get().getEmail());

        // 이메일 "kitty@gmail.com" 존재 여부 확인
        boolean emailExists = userRepository.existsByEmail("kitty@gmail.com");
        assertTrue(emailExists);

        // 닉네임 "쿠로미" 존재 여부 확인
        boolean nicknameExists = userRepository.existsByNickname("키티");
        assertTrue(nicknameExists);

        // 저장된 유저들 로그에 출력
        logger.info("저장된 유저들:");
        for (User user : users) {
            logger.info("User ID: {}, Nickname: {}, Email: {}", user.getId(), user.getNickname(), user.getEmail());
        }

        // 유저 정보 로그에 출력
        logger.info("Found User1: ID={}, Nickname={}, Email={}",
                foundUser1.map(User::getId).orElse(null),
                foundUser1.map(User::getNickname).orElse(null),
                foundUser1.map(User::getEmail).orElse(null));

        logger.info("Found User2: ID={}, Nickname={}, Email={}",
                foundUser2.map(User::getId).orElse(null),
                foundUser2.map(User::getNickname).orElse(null),
                foundUser2.map(User::getEmail).orElse(null));

        logger.info("Email exists: {}", emailExists);

        logger.info("Nickname exists: {}", nicknameExists);
    }
    }
