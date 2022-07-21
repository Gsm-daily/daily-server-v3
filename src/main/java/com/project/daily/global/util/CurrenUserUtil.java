package com.project.daily.global.util;

import com.project.daily.domain.sign.User;
import com.project.daily.domain.sign.repository.UserRepository;
import com.project.daily.global.exeception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static com.project.daily.global.exeception.ErrorCode.EMAIL_NOT_FOUND;
import static com.project.daily.global.exeception.ErrorCode.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class CurrenUserUtil {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            email = ((User) principal).getEmail();
        } else {
            email = principal.toString();
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user not found", USER_NOT_FOUND));
    }
}
