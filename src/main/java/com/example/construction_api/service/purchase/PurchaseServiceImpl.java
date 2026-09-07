package com.example.construction_api.service.purchase;

import com.example.construction_api.model.persisitece.Purchase;
import com.example.construction_api.model.requests.PurchaseCriteria;
import com.example.construction_api.model.requests.PurchasePage;
import com.example.construction_api.repository.PurchaseCriteriaRepository;
import com.example.construction_api.repository.PurchaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    private final PurchaseRepository purchaseRepository;
    private final PurchaseCriteriaRepository purchaseCriteriaRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, PurchaseCriteriaRepository purchaseCriteriaRepository) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseCriteriaRepository = purchaseCriteriaRepository;
    }

    @Override
    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    @Override
    public Purchase findById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId).orElseThrow();
    }

    @Override
    public void deleteById(Long purchaseId) {
        purchaseRepository.deleteById(purchaseId);
    }

    @Override
    public Page<Purchase> getAllWithFilters(PurchasePage purchasePage, PurchaseCriteria purchaseCriteria) {
        return purchaseCriteriaRepository.findAllWithFilters(purchasePage, purchaseCriteria);
    }

    @Override
    public Long sumPurchaseCostsBetweenTwoDatesInMillis(Long startDate, Long endDate) {
        return purchaseRepository.sumPurchaseCostsBetweenTwoDatesInMillis(startDate, endDate);
    }
}
