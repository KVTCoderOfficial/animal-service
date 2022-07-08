package ru.kvt.animalservice.dto.errors;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    USER_ALREADY_EXISTS("Пользователь уже существует"),
    USER_NOT_FOUND("Пользователь не найден"),
    INVALID_REQUEST_PARAMS("Неверные параметры запроса"),
    INVALID_REQUEST("Неверный запрос"),
    USER_UNAUTHORIZED("Необходима авторизация"),
    INCORRECT_CREDENTIALS("Неверный логин или пароль"),
    INTERNAL_SERVER_ERROR("Внутренняя ошибка сервера"),
    ANIMAL_NOT_FOUND("Животное не найдено"),
    SPECIES_NOT_FOUND("Вид животного не найден"),
    ATTEMPTS_LIMIT_EXCEEDED("Превышен лимит попыток"),
    NICKNAME_ALREADY_EXISTS("Кличка уже существует"),
    CURRENT_USER_ANIMAL_NOT_FOUND("Ваше животное не найдено"),
    UNEXPECTED_ERROR("Неизвестная ошибка");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

}
