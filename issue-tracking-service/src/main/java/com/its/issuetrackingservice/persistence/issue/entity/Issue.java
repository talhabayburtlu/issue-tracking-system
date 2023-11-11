package com.its.issuetrackingservice.persistence.issue.entity;

import com.its.issuetrackingservice.domain.issue.enums.IssueCategory;
import com.its.issuetrackingservice.persistence.common.entity.AuditableEntity;
import com.its.issuetrackingservice.persistence.sprint.entity.Sprint;
import com.its.issuetrackingservice.persistence.sprint.entity.State;
import com.its.issuetrackingservice.persistence.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.util.Set;

@Entity()
@Table(name = "issue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Issue extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_sequence")
	@SequenceGenerator(name = "issue_sequence", sequenceName = "issue_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@NotBlank
	@Column(name = "title", length = 30, nullable = false)
	private String title;

	@Column(name = "description", length = 512)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private IssueCategory category;

	@Column(name = "points")
	private Integer points;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private State state;

	@ManyToOne
	@JoinColumn(name = "sprint_id", nullable = false)
	private Sprint sprint;

	@ManyToOne
	@JoinColumn(name = "creator_user_id", nullable = false)
	private User creatorUser;

	@ManyToOne
	@JoinColumn(name = "verifier_user_id", nullable = false)
	private User verifierUser;

	@OneToMany(mappedBy = "issue")
	private Set<Attachment> attachments;

	@OneToMany(mappedBy = "issue")
	private Set<Comment> comments;
}