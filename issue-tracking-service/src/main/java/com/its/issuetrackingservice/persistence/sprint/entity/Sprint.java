package com.its.issuetrackingservice.persistence.sprint.entity;

import com.its.issuetrackingservice.persistence.common.entity.AuditableEntity;
import com.its.issuetrackingservice.persistence.issue.entity.Issue;
import com.its.issuetrackingservice.persistence.user.entity.Organization;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
	private Organization organization;

	@OneToMany(mappedBy = "sprint")
	private List<Issue> issues;

}
