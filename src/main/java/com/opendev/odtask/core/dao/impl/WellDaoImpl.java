package com.opendev.odtask.core.dao.impl;

import com.opendev.odtask.connection.ConnectionProvider;
import com.opendev.odtask.core.dao.EquipmentDao;
import com.opendev.odtask.core.dao.WellDao;
import com.opendev.odtask.core.domain.Well;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WellDaoImpl implements WellDao {

    private static final String FIND_ALL = "SELECT * FROM wells";

    private static final String FIND_BY_NAME = "SELECT id FROM wells WHERE name = ?";

    private static final String INSERT_WELL = "INSERT INTO wells (name) VALUES (?)";

    private final ConnectionProvider connectionProvider;

    private final EquipmentDao equipmentDao;

    public WellDaoImpl(ConnectionProvider connectionProvider, EquipmentDao equipmentDao) {
        this.connectionProvider = connectionProvider;
        this.equipmentDao = equipmentDao;
    }


    @Override
    public List<Well> findAll() {
        List<Well> result = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(new Well(resultSet.getLong("id"), resultSet.getString("name"),
                            equipmentDao.findAllByWellId(resultSet.getLong("id"))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Long findWellIdByName(String name) {
        long id = 0L;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    id = resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void save(Well well) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_WELL)) {
            statement.setString(1, well.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
