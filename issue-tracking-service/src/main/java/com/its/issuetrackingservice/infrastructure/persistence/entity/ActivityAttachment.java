package com.its.issuetrackingservice.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

}
