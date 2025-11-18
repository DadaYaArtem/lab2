package com.banking.domain;

import java.time.LocalDateTime;

public interface Auditable {
    String getAuditId();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    String getCreatedBy();

    String getLastModifiedBy();
}
