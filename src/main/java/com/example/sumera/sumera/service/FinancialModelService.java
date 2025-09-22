package com.example.sumera.sumera.service;

import com.example.sumera.sumera.entity.FinancialModel;
import com.example.sumera.sumera.entity.ValidationProject;
import com.example.sumera.sumera.repository.FinancialModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FinancialModelService {
    
    private final FinancialModelRepository financialModelRepository;
    private final ValidationProjectService validationProjectService;
    
    public FinancialModel createFinancialModel(FinancialModel financialModel) {
        financialModel.setCreatedDate(LocalDateTime.now());
        return financialModelRepository.save(financialModel);
    }
    
    public Optional<FinancialModel> getFinancialModelById(Long modelId) {
        return financialModelRepository.findById(modelId);
    }
    
    public List<FinancialModel> getAllFinancialModels() {
        return financialModelRepository.findAll();
    }
    
    public List<FinancialModel> getFinancialModelsByProject(Long projectId) {
        return financialModelRepository.findByValidationProjectProjectId(projectId);
    }
    
    public List<FinancialModel> getFinancialModelsByType(String modelType) {
        return financialModelRepository.findByModelType(modelType);
    }
    
    public List<FinancialModel> getFinancialModelsByOwner(Long userId) {
        return financialModelRepository.findByValidationProjectIdeaOwnerUserId(userId);
    }
    
    public FinancialModel updateFinancialModel(FinancialModel financialModel) {
        return financialModelRepository.save(financialModel);
    }
    
    public void deleteFinancialModel(Long modelId) {
        financialModelRepository.deleteById(modelId);
    }
    
    public FinancialModel createModel(Long projectId, String modelType, String assumptions,
                                    String projections, String scenarios) {
        Optional<ValidationProject> projectOpt = validationProjectService.getValidationProjectById(projectId);
        
        if (projectOpt.isPresent()) {
            FinancialModel model = new FinancialModel();
            model.setValidationProject(projectOpt.get());
            model.setModelType(modelType);
            model.setAssumptions(assumptions);
            model.setProjections(projections);
            model.setScenarios(scenarios);
            
            return createFinancialModel(model);
        }
        return null;
    }
    
    public FinancialModel addExpertReview(Long modelId, String expertReview) {
        Optional<FinancialModel> modelOpt = financialModelRepository.findById(modelId);
        if (modelOpt.isPresent()) {
            FinancialModel model = modelOpt.get();
            model.setExpertReview(expertReview);
            return financialModelRepository.save(model);
        }
        return null;
    }
    
    public FinancialModel addValidationResults(Long modelId, String validationResults) {
        Optional<FinancialModel> modelOpt = financialModelRepository.findById(modelId);
        if (modelOpt.isPresent()) {
            FinancialModel model = modelOpt.get();
            model.setValidationResults(validationResults);
            return financialModelRepository.save(model);
        }
        return null;
    }
}

