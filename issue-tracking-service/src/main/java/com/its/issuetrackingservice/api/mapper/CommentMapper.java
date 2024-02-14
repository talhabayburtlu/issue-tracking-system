package com.its.issuetrackingservice.api.mapper;

import com.its.issuetrackingservice.api.config.MapStructConfig;
import com.its.issuetrackingservice.api.dto.request.CommentRequest;
import com.its.issuetrackingservice.api.dto.response.CommentResponse;
import com.its.issuetrackingservice.persistence.entity.Comment;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = {UserMapper.class})
public abstract class CommentMapper {

    @Mapping(source = "creatorUser", target = "creatorUserId")
    public abstract CommentResponse toResponse(Comment comment);

    public abstract Comment toEntity(CommentRequest commentRequest);

    public abstract List<CommentResponse> toListResponse(List<Comment> comments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Comment patchEntityViaPatchEntityRequest(CommentRequest commentRequest, @MappingTarget Comment comment);

}
