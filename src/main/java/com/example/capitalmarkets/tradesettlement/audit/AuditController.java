package com.example.capitalmarkets.tradesettlement.audit;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/audit")
@AllArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public List<AuditEventResponse> getAuditHistory(
            @RequestParam String entityType,
            @RequestParam UUID entityId
    ){
        return auditService.getAuditHistory(entityType, entityId);
    }
}
