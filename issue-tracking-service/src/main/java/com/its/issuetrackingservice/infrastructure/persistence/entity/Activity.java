package com.its.issuetrackingservice.infrastructure.persistence.entity;

import com.its.issuetrackingservice.domain.enums.ActivityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity()
@Table(name = "activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Activity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_sequence")
    @SequenceGenerator(name = "activity_sequence", sequenceName = "activity_sequence", allocationSize = 1)
    @Column(name = "id", precision = 18)
    private Long id;

    @Lob
    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", length = 64, nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User creatorUser;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ActivityItem> activityItems = new ArrayList<>();

    @OneToMany(mappedBy = "activity")
    @Builder.Default
    private Set<ActivityAttachment> attachments = new HashSet<>();

}
