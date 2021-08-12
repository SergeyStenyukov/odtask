package com.opendev.odtask.core.dao;

import com.opendev.odtask.core.domain.Well;

import java.util.List;

public interface WellDao {

    List<Well> findAll();

    Long findWellIdByName(String name);

    void save(Well well);
}
