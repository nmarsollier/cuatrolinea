package com.desarrollo.cuatrolinea.security.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tokens")
public class TokenDocument {
    @Id
    public String id;

    public String userId;

    public RecordStatus status;

    public TokenDocument(String userId) {
        super();
        this.userId = userId;
        this.status = RecordStatus.ACTIVE;
    }
}
