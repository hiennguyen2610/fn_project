package com.example.hospital_management.statics;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)

public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ");

    String name;

    @JsonValue
    public String toName() {
        return name;
    }
}
