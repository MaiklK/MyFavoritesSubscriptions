package com.example.myfavoritessubscriptions.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Запрос на создание пользователя.
 */
public record UserCreateRequest(
        @NotBlank(message = "Не указано имя пользователя")
        @Size(min = 2, max = 50, message = "Имя пользователя должно быть от 2 до 50 символов")
        String name,

        @NotBlank(message = "Не заполнен e-mail пользователя")
        @Email(message = "Не правильный адрес почты")
        String email
) {
}
