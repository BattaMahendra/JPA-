package com.mahendra.jpal.controller;

import com.mahendra.jpal.entity.Product;
import com.mahendra.jpal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ProductController - REST API for Product pagination demonstrations
 *
 * This controller demonstrates why PaginationRepository is essential:
 * - Handles large datasets efficiently
 * - Provides flexible filtering and sorting options
 * - Improves API response time and scalability
 * - Enables modern pagination-based UI experiences
 *
 * Base URL: /api/products
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Endpoint: GET /api/products
     *
     * Demonstrates basic pagination
     *
     * Query Parameters:
     *  - page: Page number (0-indexed) - default: 0
     *  - size: Items per page - default: 10
     *
     * Example: GET /api/products?page=0&size=20
     *
     * Response: Page object containing:
     *  - content: List of 20 products
     *  - totalElements: Total count of products
     *  - totalPages: Total number of pages
     *  - currentPage: Current page number
     *  - isFirst/isLast: Navigation hints
     *
     * Benefits:
     *  - Database loads only 20 records
     *  - Memory usage is minimal
     *  - Client receives small response (fast download)
     */
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Product> products = productService.getAllProductsWithPagination(page, size);
        return ResponseEntity.ok(products);
    }

    /**
     * Endpoint: GET /api/products/sorted
     *
     * Demonstrates pagination with sorting
     *
     * Query Parameters:
     *  - page: Page number (0-indexed)
     *  - size: Items per page
     *  - sortBy: Field name to sort (productName, price, stock) - default: productId
     *  - sortOrder: asc or desc - default: asc
     *
     * Example: GET /api/products/sorted?page=0&size=20&sortBy=price&sortOrder=asc
     *
     * Real-world use cases:
     *  1. Sort by price (low to high) - Budget conscious shoppers
     *  2. Sort by price (high to low) - Premium products showcase
     *  3. Sort by product name (A-Z) - Browse alphabetically
     *  4. Sort by stock (high to low) - Availability indicator
     *
     * Benefits:
     *  - Combined filtering and sorting in single query
     *  - Database handles sort operation efficiently with indexes
     *  - Provides best user experience on listing pages
     */
    @GetMapping("/sorted")
    public ResponseEntity<Page<Product>> getProductsSorted(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "productId") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder) {

        Page<Product> products = productService.getAllProductsWithSorting(page, size, sortBy, sortOrder);
        return ResponseEntity.ok(products);
    }

    /**
     * Endpoint: GET /api/products/category/{category}
     *
     * Demonstrates filtering by category with pagination
     *
     * Path Parameter:
     *  - category: Product category (e.g., Electronics, Clothing, Books)
     *
     * Query Parameters:
     *  - page: Page number (0-indexed)
     *  - size: Items per page
     *
     * Example: GET /api/products/category/Electronics?page=0&size=20
     *
     * Real-world scenario:
     *  E-commerce site has 1 million products. User navigates to "Electronics" category.
     *
     *  Without pagination:
     *   - Query: SELECT * FROM products WHERE category='Electronics' (returns 50,000 items)
     *   - Memory: Load 50,000 objects into JVM (100MB+)
     *   - Network: Send 50,000 items to client (50MB+)
     *   - Result: Browser hangs, user frustrated
     *
     *  With pagination:
     *   - Query: SELECT * FROM products WHERE category='Electronics' LIMIT 20 OFFSET 0
     *   - Memory: Load 20 objects into JVM (100KB)
     *   - Network: Send 20 items to client (100KB)
     *   - Result: Instant response, smooth browsing experience
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Product>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Product> products = productService.getProductsByCategory(category, page, size);
        return ResponseEntity.ok(products);
    }

    /**
     * Endpoint: GET /api/products/price-range
     *
     * Demonstrates filtering by price range with pagination
     *
     * Query Parameters:
     *  - minPrice: Minimum price
     *  - maxPrice: Maximum price
     *  - page: Page number (0-indexed)
     *  - size: Items per page
     *
     * Example: GET /api/products/price-range?minPrice=100&maxPrice=500&page=0&size=20
     *
     * Real-world use case:
     *  User applies price filter on shopping app
     *  Database efficiently filters using indexed columns
     *  Returns paginated results matching price criteria
     *
     * Benefits:
     *  - Database applies WHERE clause efficiently
     *  - Indexes on price column speed up query
     *  - Pagination ensures reasonable data transfer
     */
    @GetMapping("/price-range")
    public ResponseEntity<Page<Product>> getProductsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice, page, size);
        return ResponseEntity.ok(products);
    }

    /**
     * Endpoint: GET /api/products/search
     *
     * Demonstrates search functionality with pagination
     *
     * Query Parameters:
     *  - query: Search term (substring matching)
     *  - page: Page number (0-indexed)
     *  - size: Items per page
     *
     * Example: GET /api/products/search?query=laptop&page=0&size=20
     *
     * Critical real-world scenario:
     *  Consider Google search or Amazon search:
     *  - Millions of matching results possible
     *  - Without pagination: Attempt to return 1M results (impossible)
     *  - With pagination: Return first 20, allow navigation
     *
     *  Performance comparison for "laptop" query:
     *  Without pagination (BAD):
     *   - SELECT * FROM products WHERE productName LIKE '%laptop%'
     *   - Potential results: 100,000 items
     *   - Query time: 30 seconds
     *   - Transfer size: 100MB
     *   - Memory: 500MB on server
     *
     *  With pagination (GOOD):
     *   - SELECT * FROM products WHERE productName LIKE '%laptop%' LIMIT 20 OFFSET 0
     *   - Actual results returned: 20 items
     *   - Query time: 50ms
     *   - Transfer size: 100KB
     *   - Memory: 1MB on server
     *
     *  This is why major search engines use pagination!
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Product> products = productService.searchProducts(query, page, size);
        return ResponseEntity.ok(products);
    }

    /**
     * Endpoint: GET /api/products/available
     *
     * Demonstrates conditional filtering with pagination
     *
     * Query Parameters:
     *  - sortBy: Field to sort by (default: productName)
     *  - page: Page number (0-indexed)
     *  - size: Items per page
     *
     * Example: GET /api/products/available?sortBy=price&page=0&size=20
     *
     * Real-world use case:
     *  Show only "in stock" products on website
     *  Filter applied: stock > 0
     *  Results sorted and paginated for better UX
     */
    @GetMapping("/available")
    public ResponseEntity<Page<Product>> getAvailableProducts(
            @RequestParam(value = "sortBy", defaultValue = "productName") String sortBy,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Product> products = productService.getAvailableProducts(page, size, sortBy);
        return ResponseEntity.ok(products);
    }

    /**
     * Endpoint: GET /api/products/discounted
     *
     * Demonstrates filtering for special offers with pagination
     *
     * Query Parameters:
     *  - maxPrice: Maximum price for discounted products
     *  - page: Page number (0-indexed)
     *  - size: Items per page
     *
     * Example: GET /api/products/discounted?maxPrice=99&page=0&size=20
     *
     * Real-world use case:
     *  "Deals" section on e-commerce site
     *  Show products below a certain price
     *  Paginated to display attractive limited offers
     */
    @GetMapping("/discounted")
    public ResponseEntity<Page<Product>> getDiscountedProducts(
            @RequestParam Double maxPrice,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Product> products = productService.getDiscountedProducts(maxPrice, page, size);
        return ResponseEntity.ok(products);
    }

    /**
     * Endpoint: GET /api/products/{id}
     *
     * Get single product by ID
     *
     * Path Parameter:
     *  - id: Product ID
     *
     * Example: GET /api/products/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Endpoint: POST /api/products
     *
     * Create a new product
     *
     * Request Body: Product object
     * Example:
     * {
     *   "productName": "Laptop",
     *   "category": "Electronics",
     *   "price": 999.99,
     *   "stock": 50,
     *   "description": "High-performance laptop"
     * }
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Endpoint: PUT /api/products/{id}
     *
     * Update existing product
     *
     * Path Parameter:
     *  - id: Product ID
     *
     * Request Body: Updated Product object
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {
        product.setProductId(id);
        Product updated = productService.updateProduct(product);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint: DELETE /api/products/{id}
     *
     * Delete a product
     *
     * Path Parameter:
     *  - id: Product ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

