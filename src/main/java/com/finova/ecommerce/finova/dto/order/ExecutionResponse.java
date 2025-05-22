package com.finova.ecommerce.finova.dto.order;

import com.finova.ecommerce.finova.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionResponse {
    private String approvalCode;

    public static ExecutionResponse from(Transaction tran) {
        return ExecutionResponse.builder()
                .approvalCode(tran.getApprovalCode())
                .build();
    }
}