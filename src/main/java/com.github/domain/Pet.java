package com.github.domain;

import lombok.*;

import static com.github.core.IdGenerator.id;
import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(staticName = "of")
public class Pet {

    private String id;

    private String name;

    private String breed;

    private String type;

    private String color;

    private Float weight;

    private Integer age;

    public static Pet of(String name, String breed, String type, String color, Float weight, Integer age) {
        return Pet.of(id(), name, breed, type, color, weight, age);
    }

}
