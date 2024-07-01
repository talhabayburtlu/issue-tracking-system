package com.its.issuetrackingservice.persistence.entity;

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

	@Column(name = "attachmentType", nullable = false)
	@Enumerated(EnumType.STRING)
	private AttachmentType attachmentType;

}
