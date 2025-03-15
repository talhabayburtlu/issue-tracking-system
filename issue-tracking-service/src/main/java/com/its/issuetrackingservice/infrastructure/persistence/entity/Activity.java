package com.its.issuetrackingservice.infrastructure.persistence.entity;

import com.its.issuetrackingservice.domain.enums.ActivityType;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "id", description = "Id", type = "Long", example = "1")
    private Long id;

    @Lob
    @NotBlank
    @Column(name = "description", nullable = false)
    @Schema(name = "description", description = "Description of the activity", type = "String", example = "Issue has been updated [title]:  old_title -> new_title\"")
    private String description;

    @Column(name = "type", length = 64, nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(name = "type", description = "Type of the activity", type = "String", example = "UPDATE")
    private ActivityType activityType;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    @Schema(name = "issue_id", description = "The id of the issue that this activity is related to", type = "Long", example = "1")
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", nullable = false)
    @Schema(name = "creator_user_id", description = "The id of the creator user of this activity", type = "Long", example = "1")
    private User creatorUser;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ActivityItem> activityItems = new ArrayList<>();

    @OneToMany(mappedBy = "activity")
    @Builder.Default
    private Set<ActivityAttachment> attachments = new HashSet<>();

}
