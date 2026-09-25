package com.civic.entity;

import com.civic.enums.Category;
import com.civic.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String location;
    private String city;

    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private Status status = Status.REPORTED;

    @ManyToOne
    @JoinColumn(name = "reported_by")
    private User reportedBy;

    @ManyToOne
    @JoinColumn(name = "assigned_officer")
    private Officer assignedOfficer;

    @Column(length = 1000)
    private String resolutionNote;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}