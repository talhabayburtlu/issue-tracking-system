package com.its.issuetrackingservice.persistence.sprint.entity;

import com.its.issuetrackingservice.persistence.common.entity.AuditableEntity;
import com.its.issuetrackingservice.persistence.issue.entity.Issue;
import com.its.issuetrackingservice.persistence.user.entity.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity()
@Table(name = "sprint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Sprint extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sprint_sequence")
	@SequenceGenerator(name = "sprint_sequence", sequenceName = "sprint_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@NotBlank
	@Column(name = "name", length = 30, nullable = false)
	private String name;

	@Column(name = "period", nullable = false)
	private Short period;

	@Column(name = "version", nullable = false)
	private Short version;

	@ManyToOne
	@JoinColumn(name = "initial_state_id")
	private State initialState;

	@ManyToOne
	@JoinColumn(name = "organization_id")
	private Project project;

	@OneToMany(mappedBy = "sprint")
	private List<Issue> issues;

}
