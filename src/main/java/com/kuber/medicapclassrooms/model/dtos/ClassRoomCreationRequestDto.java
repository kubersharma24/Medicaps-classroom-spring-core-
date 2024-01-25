package com.kuber.medicapclassrooms.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassRoomCreationRequestDto {
    String email;
    String name;
    String description;
    String subject;
}
