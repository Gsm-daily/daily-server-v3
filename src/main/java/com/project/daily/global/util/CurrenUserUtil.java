package com.project.daily.global.util;

import com.project.daily.domain.user.User;
import com.project.daily.domain.user.repository.UserRepository;
import com.project.daily.global.exeception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static com.project.daily.global.exeception.ErrorCode.EMAIL_NOT_FOUND;

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
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));
    }
}
