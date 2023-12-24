package com.its.issuetrackingservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.its.issuetrackingservice.domain.enums.AttachmentType;
import jakarta.persistence.*;
import lombok.*;

@Entity()
@Table(name = "attachment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Attachment extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachment_sequence")
	@SequenceGenerator(name = "attachment_sequence", sequenceName = "attachment_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@Column(name = "description", length = 512, nullable = false)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "attachmentType", nullable = false)
	private AttachmentType attachmentType;

	@ManyToOne
	@JoinColumn(name = "issue_id")
	@JsonBackReference
	private Issue issue;

	@ManyToOne
	@JoinColumn(name = "comment_id")
	@JsonBackReference
	private Comment comment;
}
