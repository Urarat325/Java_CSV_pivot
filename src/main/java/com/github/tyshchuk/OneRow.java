package com.github.tyshchuk;

import java.util.*;

public class OneRow {
    private final Object index;
    private final Map<String, List<Double>> columns = new HashMap<>();
    private static List<String> listOfUniqueIDs;

    public OneRow(Object index) {
        this.index = index;
    }

    public void addValueBySensorId(String id, double value) {
        if (!columns.containsKey(id)) {
            columns.put(id, new ArrayList<>());
        }
        List<Double> values = columns.get(id);
        assert values != null;
        values.add(value);
    }

    public String getLine() {
        StringBuilder result = new StringBuilder();
        result.append(index);
        result.append(";");
        for (String id : listOfUniqueIDs) {
            if (columns.containsKey(id)) {
                double avg = columns.get(id)
                        .stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElseThrow(IllegalStateException::new);
                result.append(avg);
                result.append(";");
            } else {
                result.append(";");
            }
        }
        result.setLength(result.length() - 1);
        result.append("\n");
        return result.toString();
    }

    public static void setListOfUniqueIDs(List<String> listOfUniqueIDs) {
        OneRow.listOfUniqueIDs = listOfUniqueIDs;
    }
}
