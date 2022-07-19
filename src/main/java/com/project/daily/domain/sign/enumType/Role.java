package com.project.daily.domain.sign.enumType;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER;

    @Override
    public String getAuthority() {
        return name(); // 인증 받은 사용자의 authorities를 조회할 수 있다.
    }
}
