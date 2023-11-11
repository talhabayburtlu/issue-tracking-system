package com.its.issuetrackingservice.persistence.issue.entity;

import com.its.issuetrackingservice.persistence.common.entity.AuditableEntity;
import com.its.issuetrackingservice.persistence.user.entity.User;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity()
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Comment extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
	@SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@Column(name = "content", length = 512, nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "creator_user_id")
	private User creatorUser;

	@ManyToOne
	@JoinColumn(name = "issue_id")
	private Issue issue;

	@OneToMany(mappedBy = "comment")
	private Set<Attachment> attachments;
}
