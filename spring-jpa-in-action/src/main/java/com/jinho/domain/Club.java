package com.jinho.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Club {

    protected Club() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clubName;

    @OneToMany(mappedBy = "club", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<User> users = new ArrayList<>();

    public Club(final String clubName) {
        this.clubName = clubName;
    }

    public void join(final User user) {
        users.add(user);
        user.setClub(this);
    }
}
