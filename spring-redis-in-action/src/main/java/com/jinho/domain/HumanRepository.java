package com.jinho.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanRepository extends JpaRepository<Human, Long> {

    Optional<Human> findByName(String name);
}
