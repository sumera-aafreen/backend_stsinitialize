package com.example.sumera.sumera.service;

import com.example.sumera.sumera.entity.Idea;
import com.example.sumera.sumera.entity.User;
import com.example.sumera.sumera.repository.IdeaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class IdeaService {
    
    private final IdeaRepository ideaRepository;
    private final UserService userService;
    
    public Idea createIdea(Idea idea) {
        idea.setSubmissionDate(LocalDateTime.now());
        idea.setLastModified(LocalDateTime.now());
        return ideaRepository.save(idea);
    }
    
    public Optional<Idea> getIdeaById(Long ideaId) {
        return ideaRepository.findById(ideaId);
    }
    
    public List<Idea> getAllIdeas() {
        return ideaRepository.findAll();
    }
    
    public List<Idea> getIdeasByOwner(Long userId) {
        return ideaRepository.findByOwnerUserId(userId);
    }
    
    public List<Idea> getIdeasByConfidentialityLevel(Idea.ConfidentialityLevel confidentialityLevel) {
        return ideaRepository.findByConfidentialityLevel(confidentialityLevel);
    }
    
    public List<Idea> getIdeasByStage(String stage) {
        return ideaRepository.findByStage(stage);
    }
    
    public List<Idea> searchIdeasByTitleOrDescription(String searchTerm) {
        return ideaRepository.findByTitleOrDescriptionContaining(searchTerm, searchTerm);
    }
    
    public List<Idea> getIdeasByTargetMarket(String market) {
        return ideaRepository.findByTargetMarketContaining(market);
    }
    
    public List<Idea> getIdeasByBusinessModel(String model) {
        return ideaRepository.findByBusinessModelContaining(model);
    }
    
    public List<Idea> getIdeasByConfidentialityLevels(List<Idea.ConfidentialityLevel> levels) {
        return ideaRepository.findByConfidentialityLevelIn(levels);
    }
    
    public Idea updateIdea(Idea idea) {
        idea.setLastModified(LocalDateTime.now());
        return ideaRepository.save(idea);
    }
    
    public void deleteIdea(Long ideaId) {
        ideaRepository.deleteById(ideaId);
    }
    
    public Idea shareIdea(Long ideaId, Idea.ConfidentialityLevel newLevel) {
        Optional<Idea> ideaOpt = ideaRepository.findById(ideaId);
        if (ideaOpt.isPresent()) {
            Idea idea = ideaOpt.get();
            idea.setConfidentialityLevel(newLevel);
            idea.setLastModified(LocalDateTime.now());
            return ideaRepository.save(idea);
        }
        return null;
    }
    
    public List<Idea> getPublicIdeas() {
        return ideaRepository.findByConfidentialityLevel(Idea.ConfidentialityLevel.PUBLIC);
    }
    
    public List<Idea> getIdeasForInvestors() {
        List<Idea.ConfidentialityLevel> investorLevels = List.of(
            Idea.ConfidentialityLevel.PUBLIC,
            Idea.ConfidentialityLevel.INVESTOR_ACCESSIBLE
        );
        return ideaRepository.findByConfidentialityLevelIn(investorLevels);
    }
}

