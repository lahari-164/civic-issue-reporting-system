package com.civic.dto;

import com.civic.entity.Issue;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OfficerIssueResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String location;
    private String photoUrl;
    private String status;
    private String resolutionNote;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OfficerIssueResponse from(Issue issue) {
        OfficerIssueResponse dto = new OfficerIssueResponse();
        dto.setId(issue.getId());
        dto.setTitle(issue.getTitle());
        dto.setDescription(issue.getDescription());
        dto.setCategory(issue.getCategory().name());
        dto.setLocation(issue.getLocation());
        dto.setPhotoUrl(issue.getPhotoUrl());
        dto.setStatus(issue.getStatus().name());
        dto.setResolutionNote(issue.getResolutionNote());
        dto.setCreatedAt(issue.getCreatedAt());
        dto.setUpdatedAt(issue.getUpdatedAt());
        return dto;
        // Note: reportedBy and assignedOfficer are deliberately left out
    }
}