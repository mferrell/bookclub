package com.mferrell.bookclub.repository;

import com.mferrell.bookclub.domain.Topic;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Topic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("select topic from Topic topic where topic.user.login = ?#{principal.username}")
    List<Topic> findByUserIsCurrentUser();
}
