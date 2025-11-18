package com.banking.domain;

import java.time.LocalDateTime;
import java.util.Map;

public interface Reportable {
    Map<String, Object> generateReport(LocalDateTime startDate, LocalDateTime endDate);

    String getReportSummary();

    double calculateTotalAmount();
}
