package com.project.daily.domain.user.service.impl;

import com.project.daily.domain.user.User;
import com.project.daily.domain.user.dto.Request.EmailDto;
import com.project.daily.domain.user.dto.Request.UserLoginDto;
import com.project.daily.domain.user.dto.Request.UserSignUpDto;
import com.project.daily.domain.user.dto.Response.UserLoginResponseDto;
import com.project.daily.domain.user.repository.UserRepository;
import com.project.daily.domain.user.service.UserService;
import com.project.daily.global.exeception.CustomException;
import com.project.daily.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Transactional
    @Override
    public Long register(UserSignUpDto userSignUpDto) {
        Optional<User> findByEmail = userRepository.findByEmail(userSignUpDto.getEmail());

        if(findByEmail.isPresent()) {
            throw new CustomException(USED_EMAIL);
        }

        User user = userSignUpDto.toEntity(passwordEncoder.encode(userSignUpDto.getPassword()));
        return userRepository.save(user).getUser_id();
    }

    @Override
    public void sendEmail(EmailDto emailDto) {

        Random random = new Random();
        String authKey = String.valueOf(random.nextInt(8888) + 1111); // 1111 ~ 9999

        sendEmailText(emailDto.getEmail(), authKey);
    }

    public void sendEmailText(String email, String authKey) {

        String subject = "test";
        String text = "회원가입을 위한 인증번호는 " + authKey + "입니다. <br/>";

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

    }

    @Transactional
    @Override
    public UserLoginResponseDto login(UserLoginDto userLoginDto) {

        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(()-> new CustomException(USER_NOT_FOUND));

        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new CustomException(PASSWORD_NOT_CORRECT);
            }

        final String AccessToken = tokenProvider.generateAccessToken(user.getEmail());
        final String RefreshToken = tokenProvider.generateRefreshToken(user.getEmail());

        user.updateRefreshToken(RefreshToken);

        return UserLoginResponseDto.builder()
                .accessToken(AccessToken)
                .refreshToken(RefreshToken)
                .build();
    }
}
