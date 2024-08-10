package com.its.issuetrackingservice.infrastructure.persistence.entity;

import com.its.issuetrackingservice.domain.enums.ActivityItemType;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "field_name", length = 128)
    private String fieldName;

    @Column(name = "old_Value", length = 1024)
    private String oldValue;

    @Column(name = "new_value", length = 1024)
    private String newValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10, nullable = false)
    private ActivityItemType type;

    @ManyToOne
    @JoinColumn(name = "activity", nullable = false)
    private Activity activity;

}
