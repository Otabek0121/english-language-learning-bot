package com.example.appenglishlanguagelearning.payload;

import com.example.appenglishlanguagelearning.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSessionDTO {

    private Long chatId;

    private UserState userState;

}
