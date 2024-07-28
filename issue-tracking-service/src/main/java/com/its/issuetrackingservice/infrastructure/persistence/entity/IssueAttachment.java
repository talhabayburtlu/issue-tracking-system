package com.its.issuetrackingservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity()
@Table(name = "issue_attachment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class IssueAttachment extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_attachment_sequence")
	@SequenceGenerator(name = "issue_attachment_sequence", sequenceName = "issue_attachment_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@ManyToOne
	@Column(name = "attachment_id", nullable = false)
	private Attachment attachment;

	@ManyToOne
	@Column(name = "issue_id", nullable = false)
	private Issue issue;
}
