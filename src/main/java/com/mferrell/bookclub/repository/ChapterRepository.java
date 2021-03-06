package com.mferrell.bookclub.repository;

import com.mferrell.bookclub.domain.Chapter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Chapter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("select chapter from Chapter chapter where chapter.user.login = ?#{principal.username}")
    List<Chapter> findByUserIsCurrentUser();
}
