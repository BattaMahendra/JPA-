package com.mahendra.jpal.service;

import com.mahendra.jpal.entity.Product;
import com.mahendra.jpal.repository.jpa.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * ProductService - Service layer for Product pagination operations
 *
 * Service layer benefits:
 * - Business logic encapsulation
 * - Reusability across multiple controllers
 * - Easier testing and maintenance
 * - Consistent pagination logic
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Get all products with pagination (default 10 items per page)
     *
     * Real-world scenario:
     * When a user opens the product listing page on an e-commerce site,
     * instead of loading all products (which could be millions),
     * we load only 10-20 products for the first page.
     */
    public Page<Product> getAllProductsWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable);
    }

    /**
     * Get products with sorting
     *
     * Real-world scenario:
     * E-commerce sites allow sorting by price (low to high or high to low),
     * name, rating, etc. This method demonstrates sorted pagination.
     *
     * Example: /api/products?page=0&size=20&sortBy=price&sortOrder=asc
     */
    public Page<Product> getAllProductsWithSorting(int pageNumber, int pageSize,
                                                    String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return productRepository.findAll(pageable);
    }

    /**
     * Get products by category with pagination
     *
     * Real-world scenario:
     * User clicks on "Electronics" category, application fetches only 20 products
     * from that category instead of loading all products in that category.
     */
    public Page<Product> getProductsByCategory(String category, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByCategory(category, pageable);
    }

    /**
     * Get products within price range with pagination
     *
     * Real-world scenario:
     * User applies filter "Price: $100 - $500", the application fetches
     * only products in that price range with pagination.
     */
    public Page<Product> getProductsByPriceRange(Double minPrice, Double maxPrice,
                                                   int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    }

    /**
     * Search products by name with pagination
     *
     * Real-world scenario:
     * User searches for "laptop" in the search bar. Without pagination:
     * - Database has to search through millions of records
     * - Return thousands of matching products
     * - Client browser hangs with huge response
     *
     * With pagination:
     * - Database searches efficiently with LIMIT clause
     * - Returns only first 20 results
     * - User can navigate through pages
     */
    public Page<Product> searchProducts(String searchTerm, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.searchProductsByName(searchTerm, pageable);
    }

    /**
     * Get available products (in stock) with pagination and sorting
     *
     * Real-world scenario:
     * Show only products that are in stock, sorted by popularity or price,
     * paginated for better UI performance.
     */
    public Page<Product> getAvailableProducts(int pageNumber, int pageSize, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return productRepository.findByStockGreaterThan(0, pageable);
    }

    /**
     * Get discounted products with pagination
     *
     * Real-world scenario:
     * Show products below certain price point on a "Deals" page,
     * limited to 20 per page to improve loading speed.
     */
    public Page<Product> getDiscountedProducts(Double discountedPrice, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByPriceLessThan(discountedPrice, pageable);
    }

    /**
     * Add a new product
     * Used by admin to add products to the catalog
     */
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Update existing product
     */
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Get single product by ID
     */
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    /**
     * Delete product
     */
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}

