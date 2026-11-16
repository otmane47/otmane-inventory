package com.otmane.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String reference;

    private BigDecimal price;

    private int quantity;

    private int alertThreshold;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}