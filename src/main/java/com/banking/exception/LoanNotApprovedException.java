package com.banking.exception;

public class LoanNotApprovedException extends Exception {
    private String loanId;
    private String rejectionReason;

    public LoanNotApprovedException(String loanId, String rejectionReason) {
        super(String.format("Loan %s not approved: %s", loanId, rejectionReason));
        this.loanId = loanId;
        this.rejectionReason = rejectionReason;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }
}
