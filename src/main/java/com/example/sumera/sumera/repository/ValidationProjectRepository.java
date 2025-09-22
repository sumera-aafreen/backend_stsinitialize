package com.example.sumera.sumera.repository;

import com.example.sumera.sumera.entity.Idea;
import com.example.sumera.sumera.entity.ValidationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValidationProjectRepository extends JpaRepository<ValidationProject, Long> {
    
    Optional<ValidationProject> findByIdea(Idea idea);
    
    Optional<ValidationProject> findByIdeaIdeaId(Long ideaId);
    
    List<ValidationProject> findByStatus(ValidationProject.ProjectStatus status);
    
    @Query("SELECT vp FROM ValidationProject vp WHERE vp.idea.owner.userId = :userId")
    List<ValidationProject> findByIdeaOwnerUserId(@Param("userId") Long userId);
    
    @Query("SELECT vp FROM ValidationProject vp WHERE vp.status = :status AND vp.idea.owner.userId = :userId")
    List<ValidationProject> findByStatusAndIdeaOwnerUserId(@Param("status") ValidationProject.ProjectStatus status, @Param("userId") Long userId);
}
