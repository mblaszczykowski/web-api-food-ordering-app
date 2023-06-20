package com.mblaszczykowski.restaurant;

import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    @NotBlank
    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @NotBlank
    @Column(
            name = "description",
            nullable = false
    )
    private String description;

    @NotBlank
    @Column(
            name = "address",
            nullable = false
    )
    private String address;

    @NotBlank
    @Column(
            name = "district",
            nullable = false
    )
    private String district;

    @NotBlank
    @Column(
            name = "phoneNumber",
            nullable = false
    )
    private String phoneNumber;

    public Restaurant() {
    }

    public Restaurant(String name, String description, String address, String district, String phoneNumber) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.district = district;
        this.phoneNumber = phoneNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(address, that.address) && Objects.equals(district, that.district) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address, district, phoneNumber);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", district='" + district + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
