package com.its.issuetrackingservice.persistence.entity;

import com.its.issuetrackingservice.domain.enums.ActivityItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Entity()
@Table(name = "activity_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ActivityItem extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_sequence")
    @SequenceGenerator(name = "activity_sequence", sequenceName = "activity_sequence", allocationSize = 1)
    @Column(name = "id", precision = 18)
    private Long id;

    @NotBlank
    @Column(name = "description", length = 256, nullable = false)
    private String description;

    @Column(name = "item_type", length = 64, nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityItemType activityItemType;

    @Column(name = "field_name", length = 512)
    private String fieldName;

    @Column(name = "old_Value", length = 512)
    private String oldValue;

    @Column(name = "new_value", length = 512)
    private String newValue;

    @ManyToOne
    @JoinColumn(name = "activity", nullable = false)
    private Activity activity;

    @OneToMany(mappedBy = "activityItem")
    private Set<ActivityItemAttachment> activityItemAttachments;

}
