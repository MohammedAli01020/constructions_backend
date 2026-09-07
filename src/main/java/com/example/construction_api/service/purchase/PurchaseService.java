package com.example.construction_api.service.purchase;

import com.example.construction_api.model.persisitece.Loan;
import com.example.construction_api.model.persisitece.Purchase;
import com.example.construction_api.model.requests.LoanCriteria;
import com.example.construction_api.model.requests.LoanPage;
import com.example.construction_api.model.requests.PurchaseCriteria;
import com.example.construction_api.model.requests.PurchasePage;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

public interface PurchaseService {

    Purchase save(Purchase purchase);

    Purchase findById(Long purchaseId);

    void deleteById(Long purchaseId);

    Page<Purchase> getAllWithFilters(PurchasePage purchasePage, PurchaseCriteria purchaseCriteria);


    Long sumPurchaseCostsBetweenTwoDatesInMillis(Long startDate, Long endDate);


}
