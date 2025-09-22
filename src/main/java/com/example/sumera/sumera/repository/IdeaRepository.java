package com.example.sumera.sumera.repository;

import com.example.sumera.sumera.entity.Idea;
import com.example.sumera.sumera.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    
    List<Idea> findByOwner(User owner);
    
    List<Idea> findByOwnerUserId(Long userId);
    
    List<Idea> findByConfidentialityLevel(Idea.ConfidentialityLevel confidentialityLevel);
    
    List<Idea> findByStage(String stage);
    
    @Query("SELECT i FROM Idea i WHERE i.title LIKE %:title% OR i.description LIKE %:description%")
    List<Idea> findByTitleOrDescriptionContaining(@Param("title") String title, @Param("description") String description);
    
    @Query("SELECT i FROM Idea i WHERE i.targetMarket LIKE %:market%")
    List<Idea> findByTargetMarketContaining(@Param("market") String market);
    
    @Query("SELECT i FROM Idea i WHERE i.businessModel LIKE %:model%")
    List<Idea> findByBusinessModelContaining(@Param("model") String model);
    
    @Query("SELECT i FROM Idea i WHERE i.confidentialityLevel IN :levels")
    List<Idea> findByConfidentialityLevelIn(@Param("levels") List<Idea.ConfidentialityLevel> levels);
}

