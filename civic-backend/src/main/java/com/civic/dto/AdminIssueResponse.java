package com.civic.dto;

import com.civic.entity.Issue;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminIssueResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String city;
    private String location;
    private String photoUrl;
    private String status;
    private String resolutionNote;
    private String assignedOfficerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AdminIssueResponse from(Issue issue) {
        AdminIssueResponse dto = new AdminIssueResponse();
        dto.setId(issue.getId());
        dto.setTitle(issue.getTitle());
        dto.setDescription(issue.getDescription());
        dto.setCategory(issue.getCategory().name());
        dto.setCity(issue.getCity());
        dto.setLocation(issue.getLocation());
        dto.setPhotoUrl(issue.getPhotoUrl());
        dto.setStatus(issue.getStatus().name());
        dto.setResolutionNote(issue.getResolutionNote());
        dto.setAssignedOfficerName(issue.getAssignedOfficer() != null ? issue.getAssignedOfficer().getName() : "Unassigned");
        dto.setCreatedAt(issue.getCreatedAt());
        dto.setUpdatedAt(issue.getUpdatedAt());
        return dto;
        // reportedBy deliberately excluded — admin sees city/location instead of who reported it
    }
}