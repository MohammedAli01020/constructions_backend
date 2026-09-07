package com.example.construction_api.repository;

import com.example.construction_api.model.enums.EmployeeStatus;
import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.requests.EmployeeCriteria;
import com.example.construction_api.model.requests.EmployeePage;
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
public class EmployeeCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public EmployeeCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<Employee> findAllWithFilters(EmployeePage employeePage, EmployeeCriteria employeeCriteria) {

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);


        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);


        Predicate predicate = getPredicate(employeeCriteria, employeeRoot);

        criteriaQuery.where(predicate);

        setOrder(employeePage, criteriaQuery, employeeRoot);


        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(employeePage.getPageNumber() * employeePage.getPageSize());
        typedQuery.setMaxResults(employeePage.getPageSize());

        Pageable pageable = getPageable(employeePage);

        long employeesCount = getEmployeesCount(predicate);


        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }


    private Predicate getPredicate(EmployeeCriteria employeeCriteria, Root<Employee> employeeRoot) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(employeeCriteria.getEmployeeStatus())) {
            if (!employeeCriteria.getEmployeeStatus().isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(employeeRoot.get("employeeStatus"),
                                EmployeeStatus.valueOf(employeeCriteria.getEmployeeStatus()))
                );
            }
        }

        if (Objects.nonNull(employeeCriteria.getFullNameOrPhoneNumber())) {
            if (!employeeCriteria.getFullNameOrPhoneNumber().isEmpty()) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(employeeRoot.get("fullName"),
                                "%" + employeeCriteria.getFullNameOrPhoneNumber() + "%"),
                        criteriaBuilder.like(employeeRoot.get("phoneNumber"),
                                "%" + employeeCriteria.getFullNameOrPhoneNumber() + "%")));
            }
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


    }


    private long getEmployeesCount(Predicate predicate) {

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(EmployeePage employeePage) {

        Sort sort = Sort.by(employeePage.getSortDirection(), employeePage.getSortBy());
        return PageRequest.of(employeePage.getPageNumber(), employeePage.getPageSize(), sort);
    }

    private void setOrder(EmployeePage employeePage, CriteriaQuery<Employee> criteriaQuery, Root<Employee> employeeRoot) {

        if (employeePage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(employeeRoot.get(employeePage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(employeeRoot.get(employeePage.getSortBy())));
        }
    }


}
