package com.opendev.odtask.core.domain;

import java.util.Objects;

public class Equipment {

    private Long id;

    private String name;

    private Long wellId;

    public Equipment() {
    }

    public Equipment(String name, Long wellId) {
        this.name = name;
        this.wellId = wellId;
    }

    public Equipment(Long id, String name, Long wellId) {
        this.id = id;
        this.name = name;
        this.wellId = wellId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWellId() {
        return wellId;
    }

    public void setWellId(Long wellId) {
        this.wellId = wellId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return id.equals(equipment.id) && name.equals(equipment.name) && wellId.equals(equipment.wellId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, wellId);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wellId=" + wellId +
                '}';
    }
}
