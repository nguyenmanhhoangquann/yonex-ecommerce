package com.yonex.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stores", indexes = {
        @Index(name = "idx_store_city", columnList = "city")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(length = 30)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String openHours;

    private Double latitude;

    private Double longitude;

    @Column(length = 500)
    private String mapUrl;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}
