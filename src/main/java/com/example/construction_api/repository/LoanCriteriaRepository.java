package com.example.construction_api.repository;

import com.example.construction_api.model.persisitece.Loan;
import com.example.construction_api.model.requests.LoanCriteria;
import com.example.construction_api.model.requests.LoanPage;
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
public class LoanCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public LoanCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<Loan> findAllWithFilters(LoanPage loanPage, LoanCriteria loanCriteria) {

        CriteriaQuery<Loan> criteriaQuery = criteriaBuilder.createQuery(Loan.class);


        Root<Loan> loanRoot = criteriaQuery.from(Loan.class);


        Predicate predicate = getPredicate(loanCriteria, loanRoot);

        criteriaQuery.where(predicate);

        setOrder(loanPage, criteriaQuery, loanRoot);


        TypedQuery<Loan> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(loanPage.getPageNumber() * loanPage.getPageSize());
        typedQuery.setMaxResults(loanPage.getPageSize());

        Pageable pageable = getPageable(loanPage);

        long loansCount = getLoansCount(predicate);


        return new PageImpl<>(typedQuery.getResultList(), pageable, loansCount);
    }


    private Predicate getPredicate(LoanCriteria loanCriteria, Root<Loan> loanRoot) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(loanCriteria.getGreaterThanOrEqualToDateTime())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(loanRoot.get("dateTime"),
                            loanCriteria.getGreaterThanOrEqualToDateTime())
            );
        }


        if (Objects.nonNull(loanCriteria.getLessThanOrEqualToDateTime())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(loanRoot.get("dateTime"),
                            loanCriteria.getLessThanOrEqualToDateTime())
            );
        }


        if (Objects.nonNull(loanCriteria.getEmployeeId()) && Objects.nonNull(loanRoot.get("employee"))) {
            predicates.add(
                    criteriaBuilder.equal(loanRoot.get("employee").get("employeeId"),
                            loanCriteria.getEmployeeId())
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


    }


    private long getLoansCount(Predicate predicate) {

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Loan> countRoot = countQuery.from(Loan.class);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(LoanPage loanPage) {

        Sort sort = Sort.by(loanPage.getSortDirection(), loanPage.getSortBy());
        return PageRequest.of(loanPage.getPageNumber(), loanPage.getPageSize(), sort);
    }

    private void setOrder(LoanPage loanPage, CriteriaQuery<Loan> criteriaQuery, Root<Loan> loanRoot) {

        if (loanPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(loanRoot.get(loanPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(loanRoot.get(loanPage.getSortBy())));
        }
    }


}
