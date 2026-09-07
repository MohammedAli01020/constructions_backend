package com.example.construction_api.repository;

import com.example.construction_api.model.enums.ShiftStatus;
import com.example.construction_api.model.persisitece.Shift;
import com.example.construction_api.model.requests.ShiftCriteria;
import com.example.construction_api.model.requests.ShiftPage;
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
public class ShiftCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ShiftCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<Shift> findAllWithFilters(ShiftPage shiftPage, ShiftCriteria shiftCriteria) {

        CriteriaQuery<Shift> criteriaQuery = criteriaBuilder.createQuery(Shift.class);


        Root<Shift> shiftRoot = criteriaQuery.from(Shift.class);


        Predicate predicate = getPredicate(shiftCriteria, shiftRoot);

        criteriaQuery.where(predicate);

        setOrder(shiftPage, criteriaQuery, shiftRoot);


        TypedQuery<Shift> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(shiftPage.getPageNumber() * shiftPage.getPageSize());
        typedQuery.setMaxResults(shiftPage.getPageSize());

        Pageable pageable = getPageable(shiftPage);

        long shiftsCount = getMachinesCount(predicate);


        return new PageImpl<>(typedQuery.getResultList(), pageable, shiftsCount);
    }


    private Predicate getPredicate(ShiftCriteria shiftCriteria, Root<Shift> shiftRoot) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(shiftCriteria.getShiftStatus())) {
            if (!shiftCriteria.getShiftStatus().isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(shiftRoot.get("shiftStatus"),
                                ShiftStatus.valueOf(shiftCriteria.getShiftStatus()))
                );
            }
        }


        if (Objects.nonNull(shiftCriteria.getGreaterThanOrEqualToStartDateTime())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(shiftRoot.get("startDateTime"),
                            shiftCriteria.getGreaterThanOrEqualToStartDateTime())
            );
        }


        if (Objects.nonNull(shiftCriteria.getLessThanOrEqualToStartDateTime())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(shiftRoot.get("startDateTime"),
                            shiftCriteria.getLessThanOrEqualToStartDateTime())
            );
        }


        if (Objects.nonNull(shiftCriteria.getMachineId()) && Objects.nonNull(shiftRoot.get("machine"))) {
            predicates.add(
                    criteriaBuilder.equal(shiftRoot.get("machine").get("machineId"),
                            shiftCriteria.getMachineId())
            );
        }


        if (Objects.nonNull(shiftCriteria.getEmployeeId()) && Objects.nonNull(shiftRoot.get("employee"))) {
            predicates.add(
                    criteriaBuilder.equal(shiftRoot.get("employee").get("employeeId"),
                            shiftCriteria.getEmployeeId())
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


    }


    private long getMachinesCount(Predicate predicate) {

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Shift> countRoot = countQuery.from(Shift.class);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(ShiftPage shiftPage) {

        Sort sort = Sort.by(shiftPage.getSortDirection(), shiftPage.getSortBy());
        return PageRequest.of(shiftPage.getPageNumber(), shiftPage.getPageSize(), sort);
    }

    private void setOrder(ShiftPage shiftPage, CriteriaQuery<Shift> criteriaQuery, Root<Shift> machineRoot) {

        if (shiftPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(machineRoot.get(shiftPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(machineRoot.get(shiftPage.getSortBy())));
        }
    }


}
