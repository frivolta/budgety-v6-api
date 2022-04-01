package com.budgety.api.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "profiles", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})})
public class Profile extends DefaultEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal startingAmount;
    private String currency;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private User user;
}
