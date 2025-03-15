package com.its.issuetrackingservice.infrastructure.dto.response;

import com.its.issuetrackingservice.infrastructure.dto.AuditableEntityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CategoryResponse extends AuditableEntityDto {
	@Serial
	private static final long serialVersionUID = -3254824055423661547L;

	@EqualsAndHashCode.Include
	@Schema(name = "id", description = "The id of the category", type = "Long", example = "1")
	private Long id;

	@Schema(name = "name", description = "The name of the related category", type = "String", example = "Backend")
	private String name;

	@Schema(name = "description", description = "The description of the related category", type = "String", example = "Backend related issue category")
	private String description;

}
