package com.its.issuetrackingservice.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity()
@Table(name = "issue_subsystem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class IssueSubsystem extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_subsystem_sequence")
    @SequenceGenerator(name = "issue_subsystem_sequence", sequenceName = "issue_subsystem_sequence", allocationSize = 1)
    @Column(name = "id", precision = 18)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "subsytem_id", nullable = false)
    private Subsystem subsystem;

}
