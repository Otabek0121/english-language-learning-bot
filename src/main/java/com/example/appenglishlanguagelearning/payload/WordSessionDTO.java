package com.example.appenglishlanguagelearning.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WordSessionDTO {

    private long chatId;

    private UUID wordId;

    private String word;

}
