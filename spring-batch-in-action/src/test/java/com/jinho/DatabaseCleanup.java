package com.jinho;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DatabaseCleanup {

    @Autowired
    private EntityManager em;

    private List<String> tableNames;

    @PostConstruct
    public void setup() {
        tableNames = em.getMetamodel().getEntities().stream()
            .map(e -> {
                if (e.getJavaType().getAnnotation(Table.class) != null) {
                    return e.getJavaType().getAnnotation(Table.class).name();
                } else {
                    return e.getName();
                }
            })
            .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        for (String tableName : tableNames) {
            em.createNativeQuery("TRUNCATE TABLE " + tableName + " RESTART IDENTITY ").executeUpdate();
        }
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

}
