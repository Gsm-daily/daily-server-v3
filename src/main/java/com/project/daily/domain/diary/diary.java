package com.project.daily.domain.diary;

import com.project.daily.domain.diary.enumType.Theme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class diary {

    @Id
    @GeneratedValue
    private Long diary_id;

    @Column
    private String content;

    @Column
    private LocalDate date;

    @Enumerated(STRING) @Column(name = "theme")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "theme", joinColumns = @JoinColumn(name = "user_id"))
    private List<Theme> themes = new ArrayList<>();

}
