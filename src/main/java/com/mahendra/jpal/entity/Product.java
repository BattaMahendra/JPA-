package com.mahendra.jpal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product Entity - Used for demonstrating PaginationRepository importance
 *
 * In industry-grade applications, pagination is crucial when dealing with:
 * - Large datasets (millions of records)
 * - API responses that need to be performant
 * - UI applications that display limited data per page
 * - Reducing memory consumption and database load
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private String category;

    private Double price;

    private Integer stock;

    private String description;
}

