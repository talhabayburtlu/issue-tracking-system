package com.its.issuetrackingservice.persistence.user.entity;

import com.its.issuetrackingservice.persistence.common.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity()
@Table(name="\"user\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	@SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@NotBlank
	@Column(name = "username", nullable = false, unique = true, length = 32)
	private String username;

	@Column(name = "first_name", nullable = false, length = 32)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 32)
	private String lastName;

	@Column(name = "email", nullable = false, length = 320)
	private String email;

	@Column(name = "keycloakId", nullable = false, unique = true, length = 36)
	private String keycloakId;

}
