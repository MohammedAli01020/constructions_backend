package com.example.construction_api.repository;

import com.example.construction_api.model.persisitece.Purchase;
import com.example.construction_api.model.requests.PurchaseCriteria;
import com.example.construction_api.model.requests.PurchasePage;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class PurchaseCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public PurchaseCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<Purchase> findAllWithFilters(PurchasePage purchasePage, PurchaseCriteria purchaseCriteria) {

        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);


        Root<Purchase> purchaseRoot = criteriaQuery.from(Purchase.class);


        Predicate predicate = getPredicate(purchaseCriteria, purchaseRoot);

        criteriaQuery.where(predicate);

        setOrder(purchasePage, criteriaQuery, purchaseRoot);


        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(purchasePage.getPageNumber() * purchasePage.getPageSize());
        typedQuery.setMaxResults(purchasePage.getPageSize());

        Pageable pageable = getPageable(purchasePage);

        long purchasesCount = getPurchasesCount(predicate);


        return new PageImpl<>(typedQuery.getResultList(), pageable, purchasesCount);
    }


    private Predicate getPredicate(PurchaseCriteria purchaseCriteria, Root<Purchase> purchaseRoot) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(purchaseCriteria.getGreaterThanOrEqualToDateTime())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(purchaseRoot.get("dateTime"),
                            purchaseCriteria.getGreaterThanOrEqualToDateTime())
            );
        }


        if (Objects.nonNull(purchaseCriteria.getLessThanOrEqualToDateTime())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(purchaseRoot.get("dateTime"),
                            purchaseCriteria.getLessThanOrEqualToDateTime())
            );
        }


        if (Objects.nonNull(purchaseCriteria.getEmployerId()) && Objects.nonNull(purchaseRoot.get("employer"))) {
            predicates.add(
                    criteriaBuilder.equal(purchaseRoot.get("employer").get("employerId"),
                            purchaseCriteria.getEmployerId())
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


    }


    private long getPurchasesCount(Predicate predicate) {

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Purchase> countRoot = countQuery.from(Purchase.class);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(PurchasePage purchasePage) {

        Sort sort = Sort.by(purchasePage.getSortDirection(), purchasePage.getSortBy());
        return PageRequest.of(purchasePage.getPageNumber(), purchasePage.getPageSize(), sort);
    }

    private void setOrder(PurchasePage purchasePage, CriteriaQuery<Purchase> criteriaQuery, Root<Purchase> purchaseRoot) {

        if (purchasePage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(purchaseRoot.get(purchasePage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(purchaseRoot.get(purchasePage.getSortBy())));
        }
    }


}
