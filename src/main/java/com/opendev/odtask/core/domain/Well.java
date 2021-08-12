package com.opendev.odtask.core.domain;

import java.util.List;
import java.util.Objects;

public class Well {

    private Long id;

    private String name;

    private List<Equipment> equipmentList;

    public Well() {

    }

    public Well(String name) {
        this.name = name;
    }

    public Well(Long id, String name, List<Equipment> equipmentList) {
        this.id = id;
        this.name = name;
        this.equipmentList = equipmentList;
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

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public void addEquipment(Equipment equipment) {
        this.equipmentList.add(equipment);
    }

    public void removeEquipment(Equipment equipment) {
        this.equipmentList.remove(equipment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Well well = (Well) o;
        return id.equals(well.id) && name.equals(well.name) && equipmentList.equals(well.equipmentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, equipmentList);
    }

    @Override
    public String toString() {
        return "Well{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", equipmentList=" + equipmentList +
                '}';
    }
}
