package com.mferrell.bookclub.service.mapper;


import com.mferrell.bookclub.domain.*;
import com.mferrell.bookclub.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ChapterMapper.class, TopicMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "chapter.id", target = "chapterId")
    @Mapping(source = "topic.id", target = "topicId")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "chapterId", target = "chapter")
    @Mapping(source = "topicId", target = "topic")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
