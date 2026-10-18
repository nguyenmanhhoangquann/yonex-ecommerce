package com.yonex.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "athletes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String country;

    @Column(nullable = false, length = 50)
    private String sport;

    @Column(length = 500)
    private String avatarUrl;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(columnDefinition = "TEXT")
    private String achievements;

    @Column(length = 150)
    private String racquetsUsed;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}
