package com.itechro.cas.model.dto.finacle;

import lombok.Data;

import java.util.Objects;

@Data
public class VolumeKey {
    private Object cnyCode;
    private int year;


    public VolumeKey(Object cnyCode, int year) {
        this.cnyCode = cnyCode;
        this.year = year;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolumeKey volumeKey = (VolumeKey) o;
        return year == volumeKey.year && Objects.equals(cnyCode, volumeKey.cnyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnyCode, year);
    }
}
