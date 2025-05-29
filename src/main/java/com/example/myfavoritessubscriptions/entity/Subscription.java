package com.example.myfavoritessubscriptions.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotBlank(message = "Не указанно имя подписки")
    @Size(min = 2, max = 50, message = "Имя подписки должно быть от 2 до 50 символов")
    private String subscriptionName;

    @ManyToMany(mappedBy = "subscriptions")
    private Set<User> users;

    @Override
    public String toString() {
        return subscriptionName;
    }
}
