package ru.kvt.animalservice.dto.animal;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AnimalPageDto {

    private List<AnimalDto> content;

    private Integer totalPages;

    private Long totalElements;

    private Integer pageSize;

    private Integer pageNumber;

}
