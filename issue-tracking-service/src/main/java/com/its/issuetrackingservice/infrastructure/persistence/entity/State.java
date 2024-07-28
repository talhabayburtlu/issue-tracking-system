package com.its.issuetrackingservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity()
@Table(name = "state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class State extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "state_sequence")
	@SequenceGenerator(name = "state_sequence", sequenceName = "state_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@NotBlank
	@Column(name = "title", length = 30, nullable = false)
	private String title;

	@Column(name = "description", length = 512)
	private String description;

	@Column(name = "is_initial", nullable = false)
	private Boolean isInitial;

	@ManyToOne
	@JoinColumn(name = "next_state_id")
	private State nextState;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
}
