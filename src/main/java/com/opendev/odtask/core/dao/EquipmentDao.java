package com.opendev.odtask.core.dao;

import com.opendev.odtask.core.domain.Equipment;

import java.util.List;

public interface EquipmentDao {

    List<Equipment> findAll();

    List<Equipment> findAllByWellId(Long id);

    void save(Equipment equipment);

    Long countByWellName(String name);
}
