package com.its.issuetrackingservice.infrastructure.persistence.entity;

import com.its.issuetrackingservice.domain.enums.AttachmentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity()
@Table(name = "attachment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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
