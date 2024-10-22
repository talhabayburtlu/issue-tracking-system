package com.its.issuetrackingservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity()
@Table(name = "membership")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Membership extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_sequence")
	@SequenceGenerator(name = "membership_sequence", sequenceName = "membership_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	@ToString.Exclude
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@ToString.Exclude
	private User user;

	@OneToMany(mappedBy = "membership")
	private Set<MembershipRole> membershipRoles;
}
