package com.mblaszczykowski.review;

import com.mblaszczykowski.customer.Customer;
import com.mblaszczykowski.order.Order;
import com.mblaszczykowski.restaurant.Restaurant;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "review_time", nullable = false)
    private LocalDateTime reviewTime;

    public Review() {
    }

    public Review(Customer customer, Restaurant restaurant, Order order, String name, Integer rating, String description) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.order = order;
        this.name = name;
        this.rating = rating;
        this.description = description;
        this.reviewTime = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(LocalDateTime reviewTime) {
        this.reviewTime = reviewTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id) && Objects.equals(customer, review.customer) && Objects.equals(restaurant, review.restaurant) && Objects.equals(name, review.name) && Objects.equals(rating, review.rating) && Objects.equals(description, review.description) && Objects.equals(reviewTime, review.reviewTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, restaurant, name, rating, description, reviewTime);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", customer=" + customer +
                ", restaurant=" + restaurant +
                ", order=" + order +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", reviewTime=" + reviewTime +
                '}';
    }
}
