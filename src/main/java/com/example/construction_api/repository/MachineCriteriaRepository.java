package com.example.construction_api.repository;

import com.example.construction_api.model.enums.MachineStatus;
import com.example.construction_api.model.enums.MachineTypes;
import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.requests.MachineCriteria;
import com.example.construction_api.model.requests.MachinePage;
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
public class MachineCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public MachineCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<Machine> findAllWithFilters(MachinePage machinePage, MachineCriteria machineCriteria) {

        CriteriaQuery<Machine> criteriaQuery = criteriaBuilder.createQuery(Machine.class);


        Root<Machine> machineRoot = criteriaQuery.from(Machine.class);


        Predicate predicate = getPredicate(machineCriteria, machineRoot);

        criteriaQuery.where(predicate);

        setOrder(machinePage, criteriaQuery, machineRoot);


        TypedQuery<Machine> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(machinePage.getPageNumber() * machinePage.getPageSize());
        typedQuery.setMaxResults(machinePage.getPageSize());

        Pageable pageable = getPageable(machinePage);

        long machinesCount = getMachinesCount(predicate);


        return new PageImpl<>(typedQuery.getResultList(), pageable, machinesCount);
    }


    private Predicate getPredicate(MachineCriteria machineCriteria, Root<Machine> machineRoot) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(machineCriteria.getMachineStatus())) {
            if (!machineCriteria.getMachineStatus().isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(machineRoot.get("machineStatus"),
                                MachineStatus.valueOf(machineCriteria.getMachineStatus()))
                );
            }
        }

        if (Objects.nonNull(machineCriteria.getMachineTypes())) {
            if (!machineCriteria.getMachineTypes().isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(machineRoot.get("machineTypes"),
                                MachineTypes.valueOf(machineCriteria.getMachineTypes()))
                );
            }
        }

        if (Objects.nonNull(machineCriteria.getCode())) {
            if (!machineCriteria.getCode().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(machineRoot.get("code"),
                                "%" + machineCriteria.getCode() + "%")
                );
            }
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


    }


    private long getMachinesCount(Predicate predicate) {

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Machine> countRoot = countQuery.from(Machine.class);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(MachinePage machinePage) {

        Sort sort = Sort.by(machinePage.getSortDirection(), machinePage.getSortBy());
        return PageRequest.of(machinePage.getPageNumber(), machinePage.getPageSize(), sort);
    }

    private void setOrder(MachinePage machinePage, CriteriaQuery<Machine> criteriaQuery, Root<Machine> machineRoot) {

        if (machinePage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(machineRoot.get(machinePage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(machineRoot.get(machinePage.getSortBy())));
        }
    }


}
