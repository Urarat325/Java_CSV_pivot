package com.github.tyshchuk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Resource {
    private String outputPath;
    private String inputPath;
    private String separator;
    private List<String> columns;
    private StringTransform<?> idParser = value -> value;
    private StringTransform<?> keyParser = value -> value;
    private StringTransform<?> valueParser = value -> value;

    private HashMap<Object, OneRow> listOfRows = new HashMap<Object, OneRow>();

    public Resource(String outputPath, String inputPath, String separator) {
        this.outputPath = outputPath;
        this.inputPath = inputPath;
        this.separator = separator;
    }

    boolean createOrClearOutputFile(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                return file.createNewFile();
            } else {
                new FileWriter(path, false).close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createColumnsAndIndexRows(String indexColumn, String keyColumn) {
        try (BufferedReader bufferedReader = readCSV(false)) {
            String line = bufferedReader.readLine();

            int index = findColumnIndex(line.split(separator), indexColumn);
            int columnIndex = findColumnIndex(line.split(separator), keyColumn);

            if (columns == null) {
                columns = new ArrayList<>();
            }

            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(separator);

                if (!listOfRows.containsKey(columns[index])) {
                    listOfRows.put(columns[index], new OneRow(columns[index]));
                }


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createHeader(String key, List<String> columns) {
        StringBuilder result = new StringBuilder(key + separator);
        for (String id : columns) {
            result.append(id);
            result.append(";");
        }
        result.setLength(result.length() - 1);
        result.append("\n");
        try (OutputStreamWriter bw = new OutputStreamWriter(
                new FileOutputStream(outputPath, true),
                StandardCharsets.UTF_8)
        ) {
            bw.write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader readCSV(boolean skipFirstRow) {
        try {
            BufferedReader inputFile = new BufferedReader(new FileReader(inputPath));
            if (skipFirstRow)
                inputFile.readLine();
            return inputFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int findColumnIndex(String[] columnNames, String column) {
        for (int i = 0; i < columnNames.length; i++)
            if (column.equals(columnNames[i])) return i;
        throw new RuntimeException("Column did not find =>" + column + "<=");
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
}
