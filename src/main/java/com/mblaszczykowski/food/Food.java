package com.mblaszczykowski.food;

import com.mblaszczykowski.restaurant.Restaurant;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "is_vegetarian", nullable = false)
    private boolean isVegetarian;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Food() {
    }

    public Food(String name, String description, String category, BigDecimal price, boolean isVegetarian, Restaurant restaurant) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.isVegetarian = isVegetarian;
        this.restaurant = restaurant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", isVegetarian=" + isVegetarian +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return isVegetarian == food.isVegetarian &&
                Objects.equals(id, food.id) &&
                Objects.equals(name, food.name) &&
                Objects.equals(description, food.description) &&
                Objects.equals(category, food.category) &&
                Objects.equals(price, food.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, category, price, isVegetarian);
    }
}
