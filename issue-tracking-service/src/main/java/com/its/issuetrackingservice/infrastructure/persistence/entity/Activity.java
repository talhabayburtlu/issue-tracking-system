package com.its.issuetrackingservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    @NotBlank
    @Column(name = "description", length = 256, nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User creatorUser;

    @OneToMany(mappedBy = "activity")
    private Set<ActivityItem> activityItems;

}
