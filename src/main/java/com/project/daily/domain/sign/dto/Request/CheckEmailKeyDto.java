package com.project.daily.domain.sign.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckEmailKeyDto {

    @NotBlank
    @Size(min = 4, max = 4)
    private String key;
}
