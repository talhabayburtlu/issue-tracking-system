package com.its.issuetrackingservice.issue.entity;

import com.its.issuetrackingservice.common.entity.AuditableEntity;
import com.its.issuetrackingservice.issue.enums.AttachmentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
	private Issue issue;

	@ManyToOne
	@JoinColumn(name = "comment_id")
	private Comment comment;
}
