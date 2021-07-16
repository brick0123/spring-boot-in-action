package com.jinho.domain.query;

import java.util.List;
import lombok.Data;

@Data
public class ClubFlatResponse {

    private Long id;
    private String clubName;
    private List<UserQueryResponse> users;

    public ClubFlatResponse(final Long id, final String clubName, final List<UserQueryResponse> users) {
        this.id = id;
        this.clubName = clubName;
        this.users = users;
    }
}
