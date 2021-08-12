package com.opendev.odtask.formatter;

import com.opendev.odtask.core.domain.Equipment;
import com.opendev.odtask.core.domain.Well;

import java.util.List;

public class Formatter {

    private static final String BD_OPEN_TAG = "<dbinfo>" + System.lineSeparator();

    private static final String BD_CLOSED_TAG = "</dbinfo>" + System.lineSeparator();

    private static final String WELL_OPEN_TAG = "<well name=\"%s\" id=\"%s\">" + System.lineSeparator();

    private static final String WELL_CLOSED_TAG = "</well>" + System.lineSeparator();

    private static final String EQUIPMENT_TAG = "    <equipment name=\"%s\" id=\"%s\"/>" + System.lineSeparator();

    public Formatter() {
    }

    public static String formatDataToXml(List<Well> list) {
        StringBuilder builder = new StringBuilder();
        builder.append(BD_OPEN_TAG);
        list.stream().distinct().forEach(well -> builder
                .append(String.format(WELL_OPEN_TAG, well.getName(), well.getId()))
                .append(convertEquipmentListToXml(well.getEquipmentList())).append(WELL_CLOSED_TAG));
        builder.append(BD_CLOSED_TAG);
        return builder.toString();
    }

    private static String convertEquipmentListToXml(List<Equipment> list) {
        StringBuilder builder = new StringBuilder();
        list.forEach(equipment -> builder.append(String.format(EQUIPMENT_TAG, equipment.getName(), equipment.getId())));
        return builder.toString();
    }
}
