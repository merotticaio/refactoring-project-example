package com.github.domain;

import lombok.*;

import static com.github.core.IdGenerator.id;
import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(staticName = "of")
public class Shelter {

    private String id;

    private String name;

    private String phone;

    private String email;

    public static Shelter of(String name, String phone, String email) {
        return Shelter.of(id(), name, phone, email);
    }

}
