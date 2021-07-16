package com.jinho.domain.query;

import com.jinho.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserQueryResponse {

    private String name;
    private String content;

    public UserQueryResponse(final User user) {
        name = user.getName();
        content = user.getQuest().getContent();
    }
}
