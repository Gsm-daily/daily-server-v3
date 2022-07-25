package com.project.daily.domain.diary.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SelectThemeDto {

    private boolean grassland;
    private boolean ocean;
    private boolean home;
    private boolean fairytale;

}
