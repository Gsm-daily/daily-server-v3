package com.project.daily.domain.diary.controller.user;

import com.project.daily.domain.diary.dto.request.SelectThemeDto;
import com.project.daily.domain.diary.service.DiaryService;
import com.project.daily.global.response.ResponseService;
import com.project.daily.global.response.result.CommonResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;
    private final ResponseService responseService;

    @PostMapping("/select/theme")
    public CommonResultResponse selectTheme(@RequestBody SelectThemeDto selectThemeDto) {
        diaryService.selectTheme(selectThemeDto);
        return responseService.getSuccessResult();
    }
}
