package com.its.issuetrackingservice.infrastructure.persistence.mapper;

import com.its.issuetrackingservice.domain.enums.ParticipationType;
import com.its.issuetrackingservice.infrastructure.configuration.mapper.MapStructConfig;
import com.its.issuetrackingservice.infrastructure.dto.response.ParticipantDetailResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.ParticipantsDetailResponse;
import com.its.issuetrackingservice.infrastructure.dto.response.ParticipantsSummaryResponse;
import com.its.issuetrackingservice.infrastructure.persistence.entity.Participation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapStructConfig.class)
public abstract class ParticipatorsMapper {

    @Mapping(source = "user.id" , target = "userId")
    @Mapping(source = "issue.id" , target = "issueId")
    @Mapping(source = "participationType" , target = "type")
    public abstract ParticipantDetailResponse toParticipantDetailResponse(Participation participation);


    public ParticipantsSummaryResponse toParticipantsSummaryResponse(Set<Participation> participants) {
        ParticipantsSummaryResponse participantsSummaryResponse = new ParticipantsSummaryResponse();
        participantsSummaryResponse.setAssignees(extractParticipators(participants, ParticipationType.ASSIGNEE));
        return participantsSummaryResponse;
    }

    public ParticipantsDetailResponse toParticipantsDetailResponse(Set<Participation> participants) {
        ParticipantsDetailResponse participantsDetailResponse = new ParticipantsDetailResponse();
        participantsDetailResponse.setCreator(extractFirstParticipator(participants, ParticipationType.CREATOR));
        participantsDetailResponse.setAssignees(extractParticipators(participants, ParticipationType.ASSIGNEE));
        participantsDetailResponse.setReviewers(extractParticipators(participants, ParticipationType.REVIEWER));
        participantsDetailResponse.setVerifiers(extractParticipators(participants, ParticipationType.VERIFIER));
        participantsDetailResponse.setWatchers(extractParticipators(participants, ParticipationType.WATCHER));
        return participantsDetailResponse;
    }

    // TODO: Create mappings for ParticipatorsDetailResponse and ParticipatorsSummaryResponse


    public Set<ParticipantDetailResponse> extractParticipators(Set<Participation> participants, ParticipationType participationType) {
        return participants.stream()
                .filter(participation -> participation.getParticipationType().equals(participationType))
                .map(this::toParticipantDetailResponse)
                .collect(Collectors.toSet());
    }

    public ParticipantDetailResponse extractFirstParticipator(Set<Participation> participants, ParticipationType participationType) {
        Participation first = participants.stream()
                .filter(participation -> participation.getParticipationType().equals(participationType))
                .findFirst()
                .orElse(null);

        if (Objects.isNull(first)) {
            return null;
        }

        return this.toParticipantDetailResponse(first);
    }
}
