package com.its.issuetrackingservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity()
@Table(name = "activity_item_attachment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ActivityItemAttachment extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_item_attachment_sequence")
    @SequenceGenerator(name = "activity_item_attachment_sequence", sequenceName = "activity_item_attachment_sequence", allocationSize = 1)
    @Column(name = "id", precision = 18)
    private Long id;

    @ManyToOne
    @Column(name = "attachment_id", nullable = false)
    private Attachment attachment;

    @ManyToOne
    @Column(name = "activity_item_id", nullable = false)
    private ActivityItem activityItem;

}
