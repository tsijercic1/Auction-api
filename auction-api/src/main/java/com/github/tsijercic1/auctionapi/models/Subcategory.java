package com.github.tsijercic1.auctionapi.models;

import javax.persistence.*;

@Entity
@Table(name = "subcategories")
public class Subcategory extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Category category;

    public Subcategory() {
    }

    public Subcategory(String name) {
        this.name = name;
    }
}
