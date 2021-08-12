package com.opendev.odtask.core.dao.impl;

import com.opendev.odtask.connection.ConnectionProvider;
import com.opendev.odtask.core.dao.EquipmentDao;
import com.opendev.odtask.core.domain.Equipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDaoImpl implements EquipmentDao {

    private static final String FIND_ALL = "SELECT * FROM equipment";

    private static final String FIND_ALL_BY_WELL_ID = "SELECT * FROM equipment WHERE well_id = ?";

    private static final String INSERT_EQUIPMENT = "INSERT INTO equipment (name , well_id) VALUES (?, ?)";

    private static final String COUNT_EQUIPMENT_BY_WELL_NAME = "SELECT COUNT(*) FROM equipment LEFT JOIN wells " +
            "ON equipment.well_id = wells.id WHERE wells.name = ?";

    private final ConnectionProvider connectionProvider;

    public EquipmentDaoImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public List<Equipment> findAll() {
        List<Equipment> result = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(new Equipment(resultSet.getLong("id"),
                            resultSet.getString("name"), resultSet.getLong("well_id")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return result;
    }

    @Override
    public List<Equipment> findAllByWellId(Long wellId) {
        List<Equipment> result = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_WELL_ID)) {
            statement.setLong(1, wellId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(new Equipment(resultSet.getLong("id"),
                            resultSet.getString("name"), resultSet.getLong("well_id")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return result;
    }

    @Override
    public void save(Equipment equipment) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_EQUIPMENT)) {
            statement.setString(1, equipment.getName());
            statement.setLong(2, equipment.getWellId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long countByWellName(String name) {
        Long count = null;
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_EQUIPMENT_BY_WELL_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return count;
    }
}
