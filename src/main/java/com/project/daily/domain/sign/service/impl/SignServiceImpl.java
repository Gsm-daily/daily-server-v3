package com.project.daily.domain.sign.service.impl;

import com.project.daily.domain.sign.dto.Request.CheckEmailKeyDto;
import com.project.daily.domain.sign.service.SignService;
import com.project.daily.domain.sign.User;
import com.project.daily.domain.sign.dto.Request.EmailDto;
import com.project.daily.domain.sign.dto.Request.SignInDto;
import com.project.daily.domain.sign.dto.Request.SignUpDto;
import com.project.daily.domain.sign.dto.Response.SignInResponseDto;
import com.project.daily.domain.sign.repository.UserRepository;
import com.project.daily.global.exeception.exceptions.KeyNotCorrectException;
import com.project.daily.global.exeception.exceptions.PasswordNotCorrectException;
import com.project.daily.global.exeception.exceptions.UsedEmailException;
import com.project.daily.global.exeception.exceptions.UserNotFoundException;
import com.project.daily.global.security.jwt.TokenProvider;
import com.project.daily.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;

import static com.project.daily.global.exeception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    // 회원가입
    @Transactional
    @Override
    public Long register(SignUpDto signUpDto) {
        Optional<User> findByEmail = userRepository.findByEmail(signUpDto.getEmail());

        if(findByEmail.isPresent()) {
            throw new UsedEmailException("used email", USED_EMAIL);
        }

        User user = signUpDto.toEntity(passwordEncoder.encode(signUpDto.getPassword()));
        return userRepository.save(user).getUser_id();
    }

    // 회원가입 때 보낼 인증번호
    @Async
    @Override
    public void sendEmail(EmailDto emailDto) {

        Random random = new Random();
        String authKey = String.valueOf(random.nextInt(8888) + 1111); // 1111 ~ 9999

        sendEmailText(emailDto.getEmail(), authKey);
    }

    @Async
    public void sendEmailText(String email, String authKey) {

        String subject = "test";
        String text = "daily 인증번호는 " + authKey + "입니다. <br/>";

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true); // 포함된 텍스트가 HTML이라는 의미로 true이다.
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // 에러의 발생 근원지를 찾아서 단계별로 에러를 출력한다.
        }
        redisUtil.setDataExpire(authKey, email, 60 * 5L);
    }

    @Override
    public void checkEmailKey(CheckEmailKeyDto checkEmailKeyDto) {

        String data = redisUtil.getData(checkEmailKeyDto.getKey());

        if(data == null) {
            throw new KeyNotCorrectException("key is not correct", KEY_NOT_CORRECT);
        }

    }

    // 로그인
    @Transactional
    @Override
    public SignInResponseDto login(SignInDto signInDto) {

        User user = userRepository.findByEmail(signInDto.getEmail())
                .orElseThrow(()-> new UserNotFoundException("user not found", USER_NOT_FOUND));

        if(!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new PasswordNotCorrectException("password not correct", PASSWORD_NOT_CORRECT);
            }

        final String AccessToken = tokenProvider.generateAccessToken(user.getEmail());
        final String RefreshToken = tokenProvider.generateRefreshToken(user.getEmail());

        user.updateRefreshToken(RefreshToken);

        return SignInResponseDto.builder()
                .accessToken(AccessToken)
                .refreshToken(RefreshToken)
                .build();
    }



    // 비밀번호 찾을 때 발송하는 인증번호


}
