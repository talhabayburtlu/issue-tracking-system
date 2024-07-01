package com.its.issuetrackingservice.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity()
@Table(name = "subsystem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Subsystem extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subsystem_sequence")
    @SequenceGenerator(name = "subsystem_sequence", sequenceName = "subsystem_sequence", allocationSize = 1)
    @Column(name = "id", precision = 18)
    private Long id;

    @NotBlank
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @NotBlank
    @Column(name = "description", length = 128, nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

}
