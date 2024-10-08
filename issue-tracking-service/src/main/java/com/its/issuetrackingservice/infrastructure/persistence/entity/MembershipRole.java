package com.its.issuetrackingservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity()
@Table(name = "membership_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MembershipRole extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_role_sequence")
	@SequenceGenerator(name = "membership_role_sequence", sequenceName = "membership_role_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "membership_id", nullable = false)
	private Membership membership;

	@NotBlank
	@Column(name = "roleName", length = 64, nullable = false)
	private String roleName;
}
