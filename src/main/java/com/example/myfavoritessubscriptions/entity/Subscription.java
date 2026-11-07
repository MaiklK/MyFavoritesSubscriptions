package com.example.myfavoritessubscriptions.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@ToString
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subscription_name", nullable = false, unique = true)
    private String subscriptionName;

    @Builder.Default
    @ToString.Exclude
    @ManyToMany(mappedBy = "subscriptions")
    private Set<User> users = new LinkedHashSet<>();

    /**
     * Равенство определяется бизнес-ключом {@code subscriptionName} в нижнем регистре:
     * сущность может быть ещё не сохранена и не иметь id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subscription other)) {
            return false;
        }
        return subscriptionName != null
                && other.subscriptionName != null
                && subscriptionName.equalsIgnoreCase(other.subscriptionName);
    }

    @Override
    public int hashCode() {
        return subscriptionName == null
                ? System.identityHashCode(this)
                : subscriptionName.toLowerCase().hashCode();
    }
}
