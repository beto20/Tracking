package com.example.demo.model.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "Audit")
public class AuditDocument {
    @Id
    private String id;
    private String traceId;
    private String timestamp;

    public AuditDocument(Builder builder) {
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

        public AuditDocument build() {
            return new AuditDocument(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
