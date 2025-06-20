package com.example.appenglishlanguagelearning.payload;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddWordSessionDTO {

    private Long chatId;

    private String word;

    private String translation;

    private String description;
}
