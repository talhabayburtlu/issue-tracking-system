package com.its.issuetrackingservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class IssueAttachment extends Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_attachment_sequence")
	@SequenceGenerator(name = "issue_attachment_sequence", sequenceName = "issue_attachment_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "issue_id", nullable = false)
	private Issue issue;
}
