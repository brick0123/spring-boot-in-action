package com.jinho.domain.query;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClubQueryResponse {

    private Long id;
    private String clubName;
    private List<UserQueryResponse> users;

    public ClubQueryResponse(final Long id, final String clubName, final List<UserQueryResponse> users) {
        this.id = id;
        this.clubName = clubName;
        this.users = users;
    }
}
