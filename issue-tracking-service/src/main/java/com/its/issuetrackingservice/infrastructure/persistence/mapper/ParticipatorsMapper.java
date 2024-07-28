package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.service.SprintService;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapStructConfig.class)
public abstract class ParticipatorsMapper {

    @Setter(onMethod_ = @Autowired)
    protected SprintService sprintService;

    // TODO: Create mappings for ParticipatorsDetailResponse and ParticipatorsSummaryResponse
}
