package com.example.construction_api.controller;

import com.example.construction_api.model.persisitece.Employer;
import com.example.construction_api.model.persisitece.Purchase;
import com.example.construction_api.model.requests.CreatePurchaseRequest;
import com.example.construction_api.model.requests.PurchaseCriteria;
import com.example.construction_api.model.requests.PurchasePage;
import com.example.construction_api.service.employer.EmployerService;
import com.example.construction_api.service.purchase.PurchaseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/purchases/")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final EmployerService employerService;

    public PurchaseController(PurchaseService purchaseService, EmployerService employerService) {
        this.purchaseService = purchaseService;
        this.employerService = employerService;
    }


    @Transactional
    @PostMapping("modify")
    public ResponseEntity<Purchase> modify(@Valid @RequestBody CreatePurchaseRequest createPurchaseRequest) {

        Purchase purchase = new Purchase();
        if (createPurchaseRequest.getPurchaseId() != null) {
            purchase = purchaseService.findById(createPurchaseRequest.getPurchaseId());
            if (purchase == null) {
                return ResponseEntity.notFound().build();
            }
        }

        Employer employer = null;
        if (createPurchaseRequest.getEmployerId() != null) {
            employer = employerService.findEmployerById(createPurchaseRequest.getEmployerId());
            if (employer == null) {
                return ResponseEntity.notFound().build();
            }
        }

        purchase.setCost(createPurchaseRequest.getCost());
        purchase.setDescription(createPurchaseRequest.getDescription());
        purchase.setDateTime(createPurchaseRequest.getDateTime());
        purchase.setEmployer(employer);

        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.OK);

    }


    @GetMapping("all")
    public ResponseEntity<Page<Purchase>> getAllLoansWithFilters(PurchasePage purchasePage, PurchaseCriteria purchaseCriteria) {
        return new ResponseEntity<>(purchaseService.
                getAllWithFilters(purchasePage, purchaseCriteria), HttpStatus.OK);
    }

    @DeleteMapping("delete/id/{purchaseId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long purchaseId) {
        purchaseService.deleteById(purchaseId);
    }
}
