package ru.kvt.animalservice.dto.errors;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDto {

    private Date timestamp;

    private Integer status;

    private String error;

    private String message;

    public ErrorDto(Integer status, String error, String message) {
        this.timestamp = new Date();
        this.status = status;
        this.error = error;
        this.message = message;
    }

}
