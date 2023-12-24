package com.its.issuetrackingservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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
	@JsonBackReference
	private Issue issue;

	@OneToMany(mappedBy = "comment")
	private Set<Attachment> attachments;
}
