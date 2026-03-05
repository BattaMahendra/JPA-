# PaginationRepository Demonstration API

## Overview
This project demonstrates the importance and critical role of `PaginationRepository` (PagingAndSortingRepository) in industry-grade applications using Spring Data JPA.

## Why PaginationRepository is Important?

### 1. **Memory Efficiency**
- **Without Pagination**: Loading 1 million products into memory at once
  ```java
  List<Product> products = repository.findAll(); // Loads all 1M products!
  // JVM Memory: 500MB+ consumed
  // Server becomes unresponsive
  ```
- **With Pagination**: Loading only 20 products at a time
  ```java
  Page<Product> products = repository.findAll(PageRequest.of(0, 20));
  // JVM Memory: 1MB consumed
  // Server handles thousands of concurrent requests
  ```

### 2. **Database Performance**
- **Query Execution Time Improvement**:
  - Without pagination: `SELECT * FROM products` → 30 seconds (1M rows)
  - With pagination: `SELECT * FROM products LIMIT 20 OFFSET 0` → 50ms

- **Database Resource Usage**:
  - Reduced CPU and I/O operations
  - Better query caching effectiveness
  - Faster database response times

### 3. **Network Bandwidth**
- **Without Pagination**: Transfer 1 million records
  - Response size: 100MB+
  - Transfer time: Several seconds
  - Client hangs waiting for response

- **With Pagination**: Transfer only 20 records
  - Response size: 100KB
  - Transfer time: Milliseconds
  - Instant UI updates

### 4. **User Experience**
- Faster page load times
- Incremental data loading (progressive enhancement)
- Better for mobile devices with limited bandwidth
- Reduced server load per request
- Better scalability for concurrent users

### 5. **Scalability**
- Handle billions of records without performance degradation
- Linear growth in response time instead of exponential
- Support for millions of concurrent users
- Infrastructure costs reduction

## Real-World Industry Examples

### E-Commerce Platform (Amazon-like)
```
Scenario: User searches for "laptop" 
Results: 100,000+ matches

WITHOUT Pagination:
├─ SQL: SELECT * FROM products WHERE name LIKE '%laptop%'
├─ Time: 45 seconds
├─ Memory: 300MB
├─ Network: 80MB
└─ Result: Browser timeout, frustrated user

WITH Pagination:
├─ SQL: SELECT * FROM products WHERE name LIKE '%laptop%' LIMIT 20 OFFSET 0
├─ Time: 50ms
├─ Memory: 500KB
├─ Network: 100KB
└─ Result: Instant results, smooth scrolling
```

### Social Media Feed (Facebook-like)
```
Scenario: Display user's feed
Data: User has 500M posts across all follows

WITHOUT Pagination:
└─ Would be physically impossible to load

WITH Pagination:
├─ Load 20-50 posts initially
├─ Load more on scroll (infinite scroll)
├─ Memory efficient
└─ Works smoothly on all devices
```

### SaaS Analytics Dashboard
```
Scenario: Show transaction history
Data: User account has 1 million transactions

WITHOUT Pagination:
├─ Export takes 2 minutes
├─ Browser memory: 500MB
└─ UI becomes unresponsive

WITH Pagination:
├─ Show first 50 transactions
├─ Sort and filter quickly
├─ Export paginated (chunk-based)
└─ Smooth user experience
```

## Created Components

### 1. **Product Entity** (`Product.java`)
- Represents a product in the system
- Contains: productId, productName, category, price, stock, description
- Used for pagination demonstrations

### 2. **ProductRepository** (`ProductRepository.java`)
- Extends `JpaRepository` and `PagingAndSortingRepository`
- Provides pagination-enabled query methods:
  - `findByCategory()` - Filter by category
  - `findByPriceBetween()` - Price range filtering
  - `searchProductsByName()` - Full-text search
  - `findByStockGreaterThan()` - Availability filtering

### 3. **ProductService** (`ProductService.java`)
- Business logic layer
- Key methods:
  - `getAllProductsWithPagination()` - Basic pagination
  - `getAllProductsWithSorting()` - Pagination with sorting
  - `getProductsByCategory()` - Category-based pagination
  - `getProductsByPriceRange()` - Price filtering with pagination
  - `searchProducts()` - Search with pagination
  - `getAvailableProducts()` - Stock-based filtering
  - `getDiscountedProducts()` - Special offers

### 4. **ProductController** (`ProductController.java`)
- REST API endpoints
- Base URL: `/api/products`

## API Endpoints

### 1. **GET /api/products**
Get all products with pagination
```
Query Parameters:
- page: Page number (0-indexed) - default: 0
- size: Items per page - default: 10

Example: GET /api/products?page=0&size=20

Response:
{
  "content": [...],
  "pageable": {...},
  "totalElements": 1000,
  "totalPages": 50,
  "number": 0,
  "size": 20,
  "hasNext": true,
  "hasPrevious": false
}
```

### 2. **GET /api/products/sorted**
Products with sorting and pagination
```
Query Parameters:
- page: Page number (0-indexed)
- size: Items per page
- sortBy: Field name (productName, price, stock)
- sortOrder: asc or desc

Example: GET /api/products/sorted?page=0&size=20&sortBy=price&sortOrder=asc
```

### 3. **GET /api/products/category/{category}**
Products filtered by category with pagination
```
Path Parameter:
- category: Product category

Example: GET /api/products/category/Electronics?page=0&size=20

Use Case: E-commerce category browsing
- Instead of loading all 50,000 Electronics products
- Load only 20 per page
- Fast navigation through pages
```

### 4. **GET /api/products/price-range**
Products within price range with pagination
```
Query Parameters:
- minPrice: Minimum price
- maxPrice: Maximum price
- page: Page number
- size: Items per page

Example: GET /api/products/price-range?minPrice=100&maxPrice=500&page=0&size=20

Use Case: Price filter on shopping sites
```

### 5. **GET /api/products/search**
Search products by name with pagination
```
Query Parameters:
- query: Search term
- page: Page number
- size: Items per page

Example: GET /api/products/search?query=laptop&page=0&size=20

Why pagination is critical here:
- Without pagination: Could return 100,000+ results
- With pagination: Returns 20, allows browsing
- Performance: 50ms instead of 30 seconds
```

### 6. **GET /api/products/available**
Show in-stock products with pagination
```
Query Parameters:
- sortBy: Field to sort
- page: Page number
- size: Items per page

Example: GET /api/products/available?sortBy=price&page=0&size=20
```

### 7. **GET /api/products/discounted**
Show discounted products with pagination
```
Query Parameters:
- maxPrice: Maximum price for discount
- page: Page number
- size: Items per page

Example: GET /api/products/discounted?maxPrice=99&page=0&size=20
```

### 8. **GET /api/products/{id}**
Get single product by ID
```
Example: GET /api/products/1
```

### 9. **POST /api/products**
Create new product
```
Request Body:
{
  "productName": "Laptop",
  "category": "Electronics",
  "price": 999.99,
  "stock": 50,
  "description": "High-performance laptop"
}
```

### 10. **PUT /api/products/{id}**
Update product
```
Path Parameter: id
Request Body: Updated product data
```

### 11. **DELETE /api/products/{id}**
Delete product
```
Path Parameter: id
```

## Key Pagination Concepts

### Page Object
The `Page` object contains:
- **content**: List of items for current page
- **totalElements**: Total number of items in database
- **totalPages**: Total number of pages
- **number**: Current page number (0-indexed)
- **size**: Items per page
- **hasNext**: Whether more pages exist
- **hasPrevious**: Whether previous page exists
- **isFirst**: Is this the first page?
- **isLast**: Is this the last page?

### Pageable Interface
Encapsulates pagination and sorting information:
- Page number and size
- Sort criteria
- Direction (ASC/DESC)
- Field name for sorting

### Example Pagination in Code
```java
// Create pageable for page 0, 20 items, sorted by price ascending
Pageable pageable = PageRequest.of(0, 20, Sort.by("price").ascending());

// Execute query with pagination
Page<Product> page = repository.findAll(pageable);

// Access results
List<Product> products = page.getContent();
long total = page.getTotalElements();
int totalPages = page.getTotalPages();
```

## Performance Metrics Comparison

### Scenario: E-commerce with 1 Million Products

#### Without Pagination
```
Memory Usage:      500MB per request
Database Query:    SELECT * FROM products (1M rows)
Query Time:        30 seconds
Network Transfer:  100MB
CPU Usage:         80%
Can Handle:        10 concurrent users
```

#### With Pagination (20 items)
```
Memory Usage:      2MB per request
Database Query:    SELECT * FROM products LIMIT 20 OFFSET 0
Query Time:        50ms
Network Transfer:  100KB
CPU Usage:         5%
Can Handle:        10,000 concurrent users
```

**Result**: 250x improvement in memory, 600x improvement in query time!

## Database Setup

### Required Tables
The application uses Spring Data JPA, so tables are created automatically if using `spring.jpa.hibernate.ddl-auto=create` in `application.properties`:

```sql
CREATE TABLE products (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    price DOUBLE,
    stock INTEGER,
    description VARCHAR(500)
);

-- Add indexes for better pagination performance
CREATE INDEX idx_category ON products(category);
CREATE INDEX idx_price ON products(price);
CREATE INDEX idx_stock ON products(stock);
CREATE FULLTEXT INDEX idx_product_name ON products(product_name);
```

## Configuration in application.properties

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/jpa_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Pagination optimization
spring.jpa.properties.hibernate.jdbc.fetch_size=20
spring.jpa.properties.hibernate.jdbc.batch_size=20
```

## Industry Best Practices

### 1. **Default Page Size**
- **Never** default to large page sizes
- Recommended: 10-50 items per page
- Depends on data size (large objects → smaller page)

### 2. **Maximum Page Size**
Implement limits to prevent abuse:
```java
if (size > 100) {
    size = 100; // Cap at 100 items
}
```

### 3. **Offset Limitations**
For very large datasets (millions of records):
- Traditional offset-based pagination becomes slow at high offsets
- Solution: Use cursor-based pagination or elasticsearch

### 4. **Query Optimization**
```java
// GOOD - Only select needed columns
@Query("SELECT new com.mahendra.jpal.dto.ProductDTO(p.id, p.name, p.price) " +
       "FROM Product p WHERE p.category = :category")
Page<ProductDTO> findByCategory(@Param("category") String category, Pageable pageable);

// BAD - Fetches all columns including large descriptions
Page<Product> findByCategory(String category, Pageable pageable);
```

### 5. **Sorting Best Practices**
- Always create indexes on frequently sorted columns
- Limit sortable fields
- Validate sort field from whitelist
```java
private static final Set<String> SORTABLE_FIELDS = 
    Set.of("productId", "price", "productName");

if (!SORTABLE_FIELDS.contains(sortBy)) {
    sortBy = "productId";
}
```

### 6. **Caching with Pagination**
```java
@Cacheable(value = "products", key = "#pageable")
public Page<Product> getAllProducts(Pageable pageable) {
    return productRepository.findAll(pageable);
}
```

## Testing the API

### Using cURL
```bash
# Get all products (page 0, 10 items)
curl "http://localhost:8080/api/products?page=0&size=10"

# Search products
curl "http://localhost:8080/api/products/search?query=laptop&page=0&size=20"

# Filter by category
curl "http://localhost:8080/api/products/category/Electronics?page=0&size=20"

# Filter by price range
curl "http://localhost:8080/api/products/price-range?minPrice=100&maxPrice=500&page=0&size=20"

# Sorted results
curl "http://localhost:8080/api/products/sorted?page=0&size=20&sortBy=price&sortOrder=asc"
```

### Using Postman
1. Import the API endpoints
2. Set query parameters:
   - page: 0
   - size: 20
   - sortBy: price
   - sortOrder: asc
3. Test different combinations

## Summary

PaginationRepository is **essential** for any production application because:

1. ✅ **Memory Efficient**: Handles datasets of any size
2. ✅ **Fast Queries**: Minimal database load
3. ✅ **Better UX**: Instant page loads
4. ✅ **Scalable**: Supports millions of concurrent users
5. ✅ **Cost-Effective**: Reduces infrastructure needs
6. ✅ **Industry Standard**: Used by all major tech companies

Without pagination, modern web applications would be impossible to build at scale!

