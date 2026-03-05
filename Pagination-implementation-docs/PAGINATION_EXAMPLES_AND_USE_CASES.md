# Pagination API - Practical Examples and Use Cases

## 🎯 Quick Reference Guide

### API Base URL
```
http://localhost:8080/api/products
```

---

## 📋 Common Use Cases with Examples

### 1. **E-Commerce Product Listing**

#### Scenario: User opens Amazon-like product page

**Request**:
```
GET /api/products?page=0&size=20
```

**What Happens**:
- Loads first 20 products
- Query: `SELECT * FROM products LIMIT 20 OFFSET 0`
- Response time: ~50ms
- Memory: 1MB

**Response**:
```json
{
  "content": [
    {
      "productId": 1,
      "productName": "Wireless Mouse",
      "category": "Electronics",
      "price": 29.99,
      "stock": 150,
      "description": "Ergonomic wireless mouse"
    },
    {
      "productId": 2,
      "productName": "USB-C Cable",
      "category": "Electronics",
      "price": 12.99,
      "stock": 300,
      "description": "High-speed USB-C charging cable"
    },
    // ... 18 more products
  ],
  "totalElements": 5000,
  "totalPages": 250,
  "number": 0,
  "size": 20,
  "hasNext": true,
  "hasPrevious": false,
  "isFirst": true,
  "isLast": false
}
```

**Why It Works**:
- Instead of loading all 5000 products, loads only 20
- User sees results instantly
- Can navigate through 250 pages
- Mobile-friendly response size

---

### 2. **Category Browsing**

#### Scenario: User clicks on "Electronics" category

**Request**:
```
GET /api/products/category/Electronics?page=0&size=20
```

**What Happens**:
- Filters products by category
- Paginates results
- Query: `SELECT * FROM products WHERE category='Electronics' LIMIT 20 OFFSET 0`

**Benefits**:
- Category has 2000 electronics products
- Shows only 20 at a time
- Database uses index on 'category' column for fast filtering
- User can browse through 100 pages comfortably

**Real-World Impact**:
```
Without Pagination:
- Load 2000 electronics products into memory
- Memory: 100MB
- Transfer: 20MB
- Browser hangs

With Pagination:
- Load 20 electronics products
- Memory: 1MB
- Transfer: 200KB
- Instant response
```

---

### 3. **Price Range Filtering**

#### Scenario: User applies "Price: $100-$500" filter

**Request**:
```
GET /api/products/price-range?minPrice=100&maxPrice=500&page=0&size=20
```

**What Happens**:
- Filters products by price range
- Applies pagination
- Query: `SELECT * FROM products WHERE price BETWEEN 100 AND 500 LIMIT 20 OFFSET 0`

**Use Cases**:
- Budget-conscious shoppers
- Sale/discount sections
- Product tier filtering (Budget, Mid-range, Premium)

**Example Navigation**:
```
User Story: Budget Shopper
1. Filter: $50-$200
2. Results: 500 matching products
3. Page 1: Products #1-20
4. Page 2: Products #21-40
5. Page 3: Products #41-60
...
25. Page 25: Products #481-500

Without pagination:
- Would try to load all 500 simultaneously
- Server memory spike: 150MB
- Network transfer: 15MB
- User frustration: Maximum

With pagination:
- Load 20 at a time
- Server memory: 1MB
- Network transfer: 200KB
- User satisfaction: Maximum
```

---

### 4. **Product Search**

#### Scenario: User searches for "laptop"

**Request**:
```
GET /api/products/search?query=laptop&page=0&size=20
```

**What Happens**:
- Searches for products matching "laptop"
- Returns paginated results
- Query: `SELECT * FROM products WHERE productName LIKE '%laptop%' LIMIT 20 OFFSET 0`

**Why Pagination is CRITICAL**:
- Without pagination: Search could return 100,000+ results
- With pagination: Returns first 20, allows navigation

**Performance Difference**:

**Without Pagination** ❌
```
Query: SELECT * FROM products WHERE productName LIKE '%laptop%'
Results found: 150,000
Query time: 45 seconds
Memory used: 600MB
Network: 80MB
Server CPU: 95%
Timeout: YES ⚠️
```

**With Pagination** ✅
```
Query: SELECT * FROM products WHERE productName LIKE '%laptop%' LIMIT 20 OFFSET 0
Results returned: 20
Query time: 100ms
Memory used: 2MB
Network: 100KB
Server CPU: 5%
Timeout: NO ✓
```

**User Experience**:
```
Search Progress:
1. User types: "laptop"
2. Without pagination: Browser spins for 45 seconds, times out
3. With pagination: Gets 20 results in 100ms
4. User can navigate pages to find specific laptop
5. Happy customer!
```

---

### 5. **Sorted Product Listings**

#### Scenario: Sort by price (low to high)

**Request**:
```
GET /api/products/sorted?page=0&size=20&sortBy=price&sortOrder=asc
```

**Response**:
```json
{
  "content": [
    { "productId": 105, "productName": "USB Hub", "price": 12.99, ... },
    { "productId": 108, "productName": "Screen Protector", "price": 14.99, ... },
    { "productId": 102, "productName": "Phone Case", "price": 19.99, ... },
    // ... sorted by price ascending, 20 items total
  ],
  "totalElements": 5000,
  "totalPages": 250,
  "number": 0,
  "size": 20,
  "hasNext": true
}
```

**Common Sorting Scenarios**:

1. **Price Low to High** (Budget Shopper)
   ```
   GET /api/products/sorted?sortBy=price&sortOrder=asc
   ```

2. **Price High to Low** (Premium Products)
   ```
   GET /api/products/sorted?sortBy=price&sortOrder=desc
   ```

3. **Alphabetical by Name**
   ```
   GET /api/products/sorted?sortBy=productName&sortOrder=asc
   ```

4. **By Stock Quantity**
   ```
   GET /api/products/sorted?sortBy=stock&sortOrder=desc
   ```

---

### 6. **Pagination Navigation Examples**

#### Scenario: User browsing product pages

**Page 1**:
```
GET /api/products?page=0&size=20
Response: Items 1-20, hasNext=true, hasPrevious=false
```

**Page 2**:
```
GET /api/products?page=1&size=20
Response: Items 21-40, hasNext=true, hasPrevious=true
```

**Page 50**:
```
GET /api/products?page=49&size=20
Response: Items 981-1000, hasNext=false, hasPrevious=true
```

**UI Pagination Implementation**:
```javascript
// JavaScript/Frontend Logic
const response = await fetch('/api/products?page=0&size=20');
const data = await response.json();

// Render products
data.content.forEach(product => {
  renderProduct(product);
});

// Render pagination controls
if (data.hasNext) {
  showNextButton();
}
if (data.hasPrevious) {
  showPreviousButton();
}
```

---

### 7. **Complex Filtering Scenarios**

#### Scenario: User applies multiple filters

**Electronics in $100-$500 range, Page 1**:
```
GET /api/products/category/Electronics?page=0&size=20
Then filter client-side by price, or create custom endpoint
```

**Available Products (In Stock), Sorted by Price**:
```
GET /api/products/available?sortBy=price&page=0&size=20
Shows only products where stock > 0
```

**Discounted Products** (Under $50):
```
GET /api/products/discounted?maxPrice=50&page=0&size=20
```

---

### 8. **Mobile App Scenarios**

#### Scenario: Loading products on slow mobile network

**Request**:
```
GET /api/products?page=0&size=10
```

**Why smaller page size**:
- Slower network
- Smaller screen
- Limited battery
- Faster initial load

**Response Size Comparison**:
- Page size 10: ~50KB ✓ (1 second on 3G)
- Page size 50: ~250KB (5 seconds on 3G)
- All products: ~5MB+ (Would timeout or crash)

---

### 9. **Admin Dashboard Scenarios**

#### Scenario: Admin viewing all orders

**Request**:
```
GET /api/products?page=0&size=50&sortBy=productId&sortOrder=desc
```

**Why pagination matters for admins**:
- Quickly scan recent products
- Page size can be larger (desktop users)
- Sorting helps identify issues
- Performance remains consistent

---

### 10. **API Response Analysis**

**Full Response Example**:
```json
{
  "content": [
    {
      "productId": 1,
      "productName": "Gaming Laptop",
      "category": "Electronics",
      "price": 1299.99,
      "stock": 15,
      "description": "High-performance gaming laptop with RTX 4090"
    },
    {
      "productId": 2,
      "productName": "Wireless Keyboard",
      "category": "Accessories",
      "price": 79.99,
      "stock": 125,
      "description": "Mechanical wireless keyboard"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": false,
  "totalElements": 5000,
  "totalPages": 250,
  "size": 20,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 20,
  "first": true,
  "empty": false,
  "hasNext": true,
  "hasPrevious": false,
  "isFirst": true,
  "isLast": false
}
```

**Understanding the Response**:
- `content`: Array of products (20 items)
- `totalElements`: 5000 total products in database
- `totalPages`: 250 (5000 ÷ 20)
- `number`: 0 (first page, 0-indexed)
- `size`: 20 (requested page size)
- `hasNext`: true (more pages available)
- `hasPrevious`: false (first page, no previous)
- `isFirst`: true (this is first page)
- `isLast`: false (not the last page)

---

## 🔍 Troubleshooting Common Issues

### Issue 1: Pagination not working

**Problem**: All products returned regardless of page size

**Solution**: Ensure repository extends PagingAndSortingRepository

```java
// ❌ WRONG
public interface ProductRepository extends JpaRepository<Product, Long> {
}

// ✅ CORRECT
public interface ProductRepository extends JpaRepository<Product, Long>, 
                                          PagingAndSortingRepository<Product, Long> {
}
```

### Issue 2: Wrong items returned

**Problem**: Page 2 doesn't return items 21-40

**Solution**: Verify page number is 0-indexed

```
Page 0 = Items 1-20
Page 1 = Items 21-40    (NOT page 2!)
Page 2 = Items 41-60    (NOT page 3!)
```

### Issue 3: Slow pagination on large datasets

**Problem**: Querying page 100 is slow

**Solution**: Add database indexes

```sql
CREATE INDEX idx_category ON products(category);
CREATE INDEX idx_price ON products(price);
```

### Issue 4: Memory issues

**Problem**: Server memory increases with page size

**Solution**: Limit maximum page size

```java
if (size > 100) {
    size = 100; // Cap at 100 items per page
}
```

---

## 📊 Performance Testing

### Test 1: Basic Pagination (1M products)
```
Page Size 20:  Query time 100ms   ✓
Page Size 100: Query time 150ms   ✓
Page Size 1000: Query time 500ms  ⚠️ Consider limiting
```

### Test 2: Filtered Pagination (100K matching products)
```
With index on filter column:  Query time 50ms   ✓
Without index:                Query time 3 seconds ❌
```

### Test 3: Sorted Pagination (1M products)
```
Sort by indexed column:   Query time 100ms   ✓
Sort by non-indexed column: Query time 2 seconds ⚠️
```

---

## ✅ Best Practices Summary

1. **Always use pagination** for list endpoints
2. **Default page size**: 10-20 items
3. **Maximum page size**: 100 items
4. **Create indexes** on frequently filtered/sorted columns
5. **Validate sort fields** from whitelist
6. **Limit offset** for very large datasets
7. **Use DTOs** instead of full entities for API responses
8. **Add caching** for frequently accessed pages
9. **Document pagination** clearly in API docs
10. **Test with real data** volumes

---

This practical guide should help you understand and effectively use the Pagination API!

