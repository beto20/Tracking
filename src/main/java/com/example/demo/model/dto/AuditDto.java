package com.example.demo.model.dto;

public class AuditDto {
    private String traceId;
    private String timestamp;

    public AuditDto(Builder builder) {
        this.traceId = builder.traceId;
        this.timestamp = builder.timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String traceId;
        private String timestamp;

        public Builder() {
        }

        public Builder setTraceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public Builder setTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public AuditDto build() {
            return new AuditDto(this);
        }
    }

    public String getTraceId() {
        return traceId;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
