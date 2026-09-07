package com.example.construction_api.repository;

import com.example.construction_api.model.persisitece.Cost;
import com.example.construction_api.model.requests.CostCriteria;
import com.example.construction_api.model.requests.CostPage;
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
public class CostCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CostCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<Cost> findAllWithFilters(CostPage costPage, CostCriteria costCriteria) {

        CriteriaQuery<Cost> criteriaQuery = criteriaBuilder.createQuery(Cost.class);


        Root<Cost> costRoot = criteriaQuery.from(Cost.class);


        Predicate predicate = getPredicate(costCriteria, costRoot);

        criteriaQuery.where(predicate);

        setOrder(costPage, criteriaQuery, costRoot);


        TypedQuery<Cost> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(costPage.getPageNumber() * costPage.getPageSize());
        typedQuery.setMaxResults(costPage.getPageSize());

        Pageable pageable = getPageable(costPage);

        long costsCount = getMaintenancesCount(predicate);


        return new PageImpl<>(typedQuery.getResultList(), pageable, costsCount);
    }


    private Predicate getPredicate(CostCriteria costCriteria, Root<Cost> costRoot) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(costCriteria.getGreaterThanOrEqualToDateTime())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(costRoot.get("dateTime"),
                            costCriteria.getGreaterThanOrEqualToDateTime())
            );
        }


        if (Objects.nonNull(costCriteria.getLessThanOrEqualToDateTime())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(costRoot.get("dateTime"),
                            costCriteria.getLessThanOrEqualToDateTime())
            );
        }


        if (Objects.nonNull(costCriteria.getMachineId()) && Objects.nonNull(costRoot.get("machine"))) {
            predicates.add(
                    criteriaBuilder.equal(costRoot.get("machine").get("machineId"),
                            costCriteria.getMachineId())
            );
        }


        if (Objects.nonNull(costCriteria.getCostTypes()) && !costCriteria.getCostTypes().isEmpty()) {
            predicates.add(
                    criteriaBuilder.equal(costRoot.get("costTypes"),
                            costCriteria.getCostTypes())
            );
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


    }


    private long getMaintenancesCount(Predicate predicate) {

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Cost> countRoot = countQuery.from(Cost.class);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(CostPage costPage) {

        Sort sort = Sort.by(costPage.getSortDirection(), costPage.getSortBy());
        return PageRequest.of(costPage.getPageNumber(), costPage.getPageSize(), sort);
    }

    private void setOrder(CostPage costPage, CriteriaQuery<Cost> criteriaQuery, Root<Cost> costRoot) {

        if (costPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(costRoot.get(costPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(costRoot.get(costPage.getSortBy())));
        }
    }


}
