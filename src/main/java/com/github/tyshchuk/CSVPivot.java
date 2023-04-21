package com.github.tyshchuk;

import java.util.List;

public class CSVPivot implements Runnable {
    private final Resource resource;
    private String inputPath;
    private String outputPath;
    private String indexColumn;
    private String keyColumn;
    private String valueColumn;
    private List<String> columns;
    private StringTransform<?> idParser = value -> value;
    private StringTransform<?> keyParser = value -> value;
    private StringTransform<?> valueParser = value -> value;
    private String separator = ",";

    public CSVPivot(String inputPath, String outputPath, String indexColumn, String keyColumn, String valueColumn, String separator) {
        this(inputPath, outputPath, indexColumn, keyColumn, valueColumn);
        this.separator = separator;
    }

    public CSVPivot(String inputPath, String outputPath, String indexColumn, String keyColumn, String valueColumn, List<String> columns) {
        this(inputPath, outputPath, indexColumn, keyColumn, valueColumn);
        this.columns = columns;
    }

    public CSVPivot(String inputPath, String outputPath, String indexColumn, String keyColumn, String valueColumn) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.indexColumn = indexColumn;
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
        this.resource = new Resource(outputPath, inputPath, this.separator);
    }

    public void build() {
        resource.createOrClearOutputFile(outputPath);
        if (columns != null) {
            resource.setColumns(columns);
        }
        resource.createColumnsAndIndexRows(indexColumn, keyColumn);

    }

    public void run() {
        build();
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getIndexColumn() {
        return indexColumn;
    }

    public void setIndexColumn(String indexColumn) {
        this.indexColumn = indexColumn;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public String getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public void setIdParser(StringTransform<?> idParser) {
        this.idParser = idParser;
    }

    public void setKeyParser(StringTransform<?> keyParser) {
        this.keyParser = keyParser;
    }

    public void setValueParser(StringTransform<?> valueParser) {
        this.valueParser = valueParser;
    }
}
