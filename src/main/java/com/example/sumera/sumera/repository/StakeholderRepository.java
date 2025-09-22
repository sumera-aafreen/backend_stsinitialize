package com.example.sumera.sumera.repository;

import com.example.sumera.sumera.entity.Stakeholder;
import com.example.sumera.sumera.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StakeholderRepository extends JpaRepository<Stakeholder, Long> {
    
    List<Stakeholder> findByUser(User user);
    
    List<Stakeholder> findByUserUserId(Long userId);
    
    List<Stakeholder> findByStakeholderType(Stakeholder.StakeholderType stakeholderType);
    
    @Query("SELECT s FROM Stakeholder s WHERE s.engagementLevel >= :minLevel")
    List<Stakeholder> findByEngagementLevelGreaterThanEqual(@Param("minLevel") Integer minLevel);
    
    @Query("SELECT s FROM Stakeholder s WHERE s.relationship LIKE %:relationship%")
    List<Stakeholder> findByRelationshipContaining(@Param("relationship") String relationship);
}

