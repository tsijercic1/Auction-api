package com.github.tsijercic1.auctionapi.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "subcategories")
public class Subcategory extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String name;
    @ManyToOne
    private Category category;

    public Subcategory() {
    }

    public Subcategory(String name) {
        this.name = name;
    }
}
