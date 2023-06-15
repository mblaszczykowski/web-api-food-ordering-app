# REST API for a Food Ordering Application in Java with Spring Boot 3 and PostgreSQL database

## Description
Project contains 7 packages - Customer, Restaurant, Food, Order, Payment, Review and Exception

The following classes are related to each other in the following ways:

| Class Relationship                           | Description                                              |
|----------------------------------------------|----------------------------------------------------------|
| Food - Restaurant                             | Many-to-One (multiple foods associated with one restaurant) |
| Order - Customer                              | Many-to-One (multiple orders associated with one customer) |
| Order - Food                                  | Many-to-Many (multiple foods associated with multiple orders) |
| Payment - Order                               | One-to-One (one payment associated with one order)         |
| Review - Customer                             | Many-to-One (multiple reviews associated with one customer) |
| Review - Restaurant                           | Many-to-One (multiple reviews associated with one restaurant) |
| Review - Order                                | One-to-One (one review associated with one order)          |

The Service classes include code that prevents assigning entities to non-existent entities. This ensures that the data remains consistent and avoids errors. For example, when adding food to a restaurant, the code checks if the restaurant exists before making the association. Similarly, when creating a review, the code verifies if the customer, restaurant, and order mentioned in the review actually exist.

## Technologies & tools used
* **Java 17**
* **Maven**
* **Spring Boot 3**
* **Spring Data JPA**
* **PostgreSQL Database**
* **Docker**
* **Postman**
* **IntelliJ Ultimate Edition**  

## Requests:

| Endpoints                                   | Description                                              |
|---------------------------------------------|----------------------------------------------------------|
| **Customers**                               |                                                          |
| `GET /api/v1/customers`                     | Retrieves all customers                                  |
| `GET /api/v1/customers/{id}`                | Retrieves the customer with the specified ID             |
| `GET /api/v1/customers/email/{email}`        | Retrieves the customer with the specified email          |
| `POST /api/v1/customers`                    | Adds a new customer                                      |
| `PUT /api/v1/customers/{id}`                | Updates the customer with the specified ID               |
| `DELETE /api/v1/customers/{id}`             | Deletes the customer with the specified ID               |
| **Restaurants**                             |                                                          |
| `GET /api/v1/restaurants`                    | Retrieves all restaurants                                |
| `GET /api/v1/restaurants/{id}`               | Retrieves the restaurant with the specified ID           |
| `GET /api/v1/restaurants/district/{district}`| Retrieves restaurants in the specified district          |
| `GET /api/v1/restaurants/name/{name}`        | Retrieves restaurants with the specified name            |
| `POST /api/v1/restaurants`                   | Adds a new restaurant                                    |
| `PUT /api/v1/restaurants/{id}`               | Updates the restaurant with the specified ID             |
| `DELETE /api/v1/restaurants/{id}`            | Deletes the restaurant with the specified ID             |
| **Food**                                    |                                                          |
| `GET /api/v1/food`                           | Retrieves all available food                             |
| `GET /api/v1/food/{id}`                      | Retrieves the food with the specified ID                 |
| `GET /api/v1/food/name/{name}`               | Retrieves food with the specified name                   |
| `GET /api/v1/food/category/{category}`       | Retrieves food with the specified category               |
| `GET /api/v1/food/type/vegetarian`           | Retrieves all vegetarian food                            |
| `GET /api/v1/food/restaurant/{restaurant_id}`| Retrieves food for the specified restaurant              |
| `GET /api/v1/food/price-range`               | Retrieves food within the specified price range          |
| `POST /api/v1/food`                          | Adds new food to the database                            |
| `PUT /api/v1/food/{id}`                      | Updates the food with the specified ID                   |
| `DELETE /api/v1/food/{id}`                   | Deletes the food with the specified ID                   |
| **Orders**                                  |                                                          |
| `GET /api/v1/orders`                         | Retrieves all orders                                     |
| `GET /api/v1/orders/{id}`                    | Retrieves the order with the specified ID                |
| `GET /api/v1/orders/customer/{customerId}`   | Retrieves orders for the specified customer              |
| `GET /api/v1/orders/restaurant/{restaurantId}`| Retrieves orders for the specified restaurant            |
| `POST /api/v1/orders`                        | Adds a new order                                         |
| `PUT /api/v1/orders/{id}`                    | Updates the order with the specified ID                  |
| **Payments**                                |                                                          |
| `GET /api/v1/payments`                       | Retrieves all payments                                   |
| `GET /api/v1/payments/{id}`                  | Retrieves the payment with the specified ID              |
| `POST /api/v1/payments`                      | Adds a new payment                                       |
| `PUT /api/v1/payments/{id}`                  | Updates the payment with the specified ID                |
| **Reviews**                                 |                                                          |
| `GET /api/v1/reviews`                        | Retrieves all reviews                                    |
| `GET /api/v1/reviews/{id}`                   | Retrieves the review with the specified ID               |
| `GET /api/v1/reviews/restaurant/{id}`         | Retrieves reviews for the specified restaurant           |
| `GET /api/v1/reviews/date/{date}`             | Retrieves reviews for the specified date                 |
| `GET /api/v1/reviews/user/{customerId}`       | Retrieves reviews for the specified customer             |
| `POST /api/v1/reviews`                       | Adds a new review                                        |
| `PUT /api/v1/reviews/{id}`                   | Updates the review with the specified ID                 |
| `DELETE /api/v1/reviews/{id}`                | Deletes the review with the specified ID                 |


**Example JSON for POST/PUT requests:**

#### Customers
```json
{
  "firstname": "Jan",
  "lastname": "Kowalski",
  "email": "jan.kowalski@example.com",
  "address": "ul. Nowa 5, 01-234 Warszawa",
  "phoneNumber": "+48 123 456 789"
}
```

#### Restaurants
```json
{
  "name": "Amo La Pasta",
  "description": "Italian restaurant",
  "address": "ul. Mokotowska 15, 01-234 Warsaw",
  "district": "Mokotów",
  "phoneNumber": "+48 222 333 444"
}
```

#### Food
```json
{
  "restaurant": {"id":"1"},
  "name": "Pizza1",
  "description": "Classic Italian pizza",
  "category": "Italian",
  "price": 10.99,
  "isVegetarian": true
}
```

#### Orders
```json
{
  "customer": {"id": 1},
  "foods": [
    {"id": 1},
    {"id": 2}
  ],
  "address": "789 Oak Street, City",
  "deliveryType": "SHIPPING"
}
```

#### Payments
```json
{
  "order": {
    "id": 1
  },
  "paymentMethod": "CASH"
}
```

#### Reviews
```json
{
  "customer": {"id": "1"},
  "restaurant": {"id": "1"},
  "order": {"id": "1"},
  "name": "Wonderful Pizza",
  "rating": 5,
  "description": "The pizza was delicious and arrived hot. Excellent service!"
}
```
