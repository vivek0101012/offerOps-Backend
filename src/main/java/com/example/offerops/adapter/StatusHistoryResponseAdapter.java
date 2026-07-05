package com.example.offerops.adapter;

import com.example.offerops.dto.StatusHistoryResponse;
import com.example.offerops.models.StatusHistory;
import org.springframework.stereotype.Component;

@Component
public class StatusHistoryResponseAdapter {

    public StatusHistoryResponse toResponse(StatusHistory statusHistory) {
        return StatusHistoryResponse.builder()
                .id(statusHistory.getId())
                .fromStatus(statusHistory.getFromStatus())
                .toStatus(statusHistory.getToStatus())
                .changedAt(statusHistory.getChangedAt())
                .notes(statusHistory.getNotes())
                .build();
    }
}