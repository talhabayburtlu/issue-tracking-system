package com.its.issuetrackingservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class ActivityAttachment extends Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_item_attachment_sequence")
    @SequenceGenerator(name = "activity_item_attachment_sequence", sequenceName = "activity_item_attachment_sequence", allocationSize = 1)
    @Column(name = "id", precision = 18)
    private Long id;

    @ManyToOne
    @Column(name = "activity_id", nullable = false)
    private Activity activity;

}
