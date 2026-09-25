package com.civic.dto;

import com.civic.entity.Issue;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PublicIssueResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String location;
    private String city;
    private String photoUrl;
    private String status;
    private String resolutionNote;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PublicIssueResponse from(Issue issue) {
        PublicIssueResponse dto = new PublicIssueResponse();
        dto.setId(issue.getId());
        dto.setTitle(issue.getTitle());
        dto.setDescription(issue.getDescription());
        dto.setCategory(issue.getCategory().name());
        dto.setLocation(issue.getLocation());
        dto.setCity(issue.getCity());
        dto.setPhotoUrl(issue.getPhotoUrl());
        dto.setStatus(issue.getStatus().name());
        dto.setResolutionNote(issue.getResolutionNote());
        dto.setCreatedAt(issue.getCreatedAt());
        dto.setUpdatedAt(issue.getUpdatedAt());
        return dto;
        // reportedBy and assignedOfficer deliberately excluded — same privacy pattern as OfficerIssueResponse
    }
}