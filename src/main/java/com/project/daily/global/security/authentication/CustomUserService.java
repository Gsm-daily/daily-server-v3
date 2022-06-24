package com.project.daily.global.security.authentication;

import com.project.daily.exeception.CustomException;
import com.project.daily.exeception.ErrorCode;
import com.project.daily.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.project.daily.exeception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
