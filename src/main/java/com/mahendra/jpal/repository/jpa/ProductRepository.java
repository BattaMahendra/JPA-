package com.mahendra.jpal.repository.jpa;

import com.mahendra.jpal.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository - Extending PagingAndSortingRepository
 *
 * Why PagingAndSortingRepository is important in industry:
 *
 * 1. **Memory Efficiency**: Instead of loading all 1 million products into memory,
 *    we fetch only 20 products per page.
 *
 * 2. **Database Performance**: Query execution time is reduced significantly.
 *    - Without pagination: SELECT * FROM products (1M rows) takes seconds
 *    - With pagination: SELECT * FROM products LIMIT 20 OFFSET 0 takes milliseconds
 *
 * 3. **Network Bandwidth**: Reduces data transfer size
 *    - Without pagination: Transfer 1M records (100MB+)
 *    - With pagination: Transfer 20 records (2KB)
 *
 * 4. **User Experience**: Better UI responsiveness with incremental data loading
 *
 * 5. **Scalability**: Applications can handle growing databases without performance degradation
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    /**
     * Find products by category with pagination
     * Use Case: E-commerce sites where users browse category-wise products
     */
    Page<Product> findByCategory(String category, Pageable pageable);

    /**
     * Find products with price less than given value with pagination
     * Use Case: Price range filtering in shopping applications
     */
    Page<Product> findByPriceLessThan(Double price, Pageable pageable);

    /**
     * Find products with price between range with pagination
     * Use Case: Budget-based product filtering
     */
    Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    /**
     * Custom query to find products by name pattern with pagination
     * Use Case: Product search functionality
     */
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:searchTerm%")
    Page<Product> searchProductsByName(String searchTerm, Pageable pageable);

    /**
     * Find products in stock (stock > 0) with pagination
     * Use Case: Available products listing
     */
    Page<Product> findByStockGreaterThan(Integer minStock, Pageable pageable);
}

