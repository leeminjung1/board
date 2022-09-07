package com.leeminjung1.domain.model.member;

import lombok.Getter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table
@Getter
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege() {
    }

    public Privilege(String name) {
        this.name = name;
    }

}