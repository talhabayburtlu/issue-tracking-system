package com.its.issuetrackingservice.persistence.entity;

import com.its.issuetrackingservice.domain.enums.ParticipationType;
import jakarta.persistence.*;
import lombok.*;

@Entity()
@Table(name = "participation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Participation extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participation_sequence")
    @SequenceGenerator(name = "participation_sequence", sequenceName = "participation_sequence", allocationSize = 1)
    @Column(name = "id", precision = 18)
    private Long id;

    @Column(name = "participation_type", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private ParticipationType participationType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Column(name = "isWatching", nullable = false, columnDefinition = "true")
    private Boolean isWatching;
}
