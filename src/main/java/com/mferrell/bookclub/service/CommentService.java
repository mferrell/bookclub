package com.mferrell.bookclub.service;

import com.mferrell.bookclub.service.dto.CommentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mferrell.bookclub.domain.Comment}.
 */
public interface CommentService {

    /**
     * Save a comment.
     *
     * @param commentDTO the entity to save.
     * @return the persisted entity.
     */
    CommentDTO save(CommentDTO commentDTO);

    /**
     * Get all the comments.
     *
     * @return the list of entities.
     */
    List<CommentDTO> findAll();


    /**
     * Get the "id" comment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentDTO> findOne(Long id);

    /**
     * Delete the "id" comment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
