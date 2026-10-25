package com.alficus;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssistantResponseDto {
    private String message;
    private String nextStep;
    private List<String> options;
    private String link;
    private TableData tableData;
    private List<String> documentsRequired;

    public static class TableData {
        private List<String> headers;
        private List<List<String>> rows;

        public TableData() {}

        public TableData(List<String> headers, List<List<String>> rows) {
            this.headers = headers;
            this.rows = rows;
        }

        public List<String> getHeaders() { return headers; }
        public void setHeaders(List<String> headers) { this.headers = headers; }

        public List<List<String>> getRows() { return rows; }
        public void setRows(List<List<String>> rows) { this.rows = rows; }
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getNextStep() { return nextStep; }
    public void setNextStep(String nextStep) { this.nextStep = nextStep; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public TableData getTableData() { return tableData; }
    public void setTableData(TableData tableData) { this.tableData = tableData; }

    public List<String> getDocumentsRequired() { return documentsRequired; }
    public void setDocumentsRequired(List<String> documentsRequired) { this.documentsRequired = documentsRequired; }

    // Builder Pattern implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final AssistantResponseDto instance = new AssistantResponseDto();

        public Builder message(String message) {
            instance.setMessage(message);
            return this;
        }

        public Builder nextStep(String nextStep) {
            instance.setNextStep(nextStep);
            return this;
        }

        public Builder options(List<String> options) {
            instance.setOptions(options);
            return this;
        }

        public Builder link(String link) {
            instance.setLink(link);
            return this;
        }

        public Builder tableData(TableData tableData) {
            instance.setTableData(tableData);
            return this;
        }

        public Builder documentsRequired(List<String> documentsRequired) {
            instance.setDocumentsRequired(documentsRequired);
            return this;
        }

        public AssistantResponseDto build() {
            return instance;
        }
    }
}