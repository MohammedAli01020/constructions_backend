package com.example.construction_api.repository;

import com.example.construction_api.model.enums.DamageStatus;
import com.example.construction_api.model.persisitece.Damage;
import com.example.construction_api.model.persisitece.Loan;
import com.example.construction_api.model.requests.DamageCriteria;
import com.example.construction_api.model.requests.DamagePage;
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
public class DamageCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public DamageCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<Damage> findAllWithFilters(DamagePage damagePage, DamageCriteria damageCriteria) {

        CriteriaQuery<Damage> criteriaQuery = criteriaBuilder.createQuery(Damage.class);


        Root<Damage> damageRoot = criteriaQuery.from(Damage.class);


        Predicate predicate = getPredicate(damageCriteria, damageRoot);

        criteriaQuery.where(predicate);

        setOrder(damagePage, criteriaQuery, damageRoot);


        TypedQuery<Damage> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(damagePage.getPageNumber() * damagePage.getPageSize());
        typedQuery.setMaxResults(damagePage.getPageSize());

        Pageable pageable = getPageable(damagePage);

        long damagesCount = getDamagesCount(predicate);


        return new PageImpl<>(typedQuery.getResultList(), pageable, damagesCount);
    }


    private Predicate getPredicate(DamageCriteria damageCriteria, Root<Damage> damageRoot) {

        List<Predicate> predicates = new ArrayList<>();


        if (Objects.nonNull(damageCriteria.getMachineId())) {
            predicates.add(
                    criteriaBuilder.equal(damageRoot.get("machine").get("machineId"),
                            damageCriteria.getMachineId())
            );
        }

        if (Objects.nonNull(damageCriteria.getDamageStatus())) {
            if (!damageCriteria.getDamageStatus().isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(damageRoot.get("damageStatus"),
                                DamageStatus.valueOf(damageCriteria.getDamageStatus()))
                );
            }

        }


        if (Objects.nonNull(damageCriteria.getGreaterThanOrEqualToInsertDateTime())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(damageRoot.get("insertDateTime"),
                            damageCriteria.getGreaterThanOrEqualToInsertDateTime())
            );
        }


        if (Objects.nonNull(damageCriteria.getLessThanOrEqualToInsertDateTime())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(damageRoot.get("insertDateTime"),
                            damageCriteria.getLessThanOrEqualToInsertDateTime())
            );
        }


        if (Objects.nonNull(damageCriteria.getGreaterThanOrEqualToStartDateTime())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(damageRoot.get("startDateTime"),
                            damageCriteria.getGreaterThanOrEqualToStartDateTime())
            );
        }


        if (Objects.nonNull(damageCriteria.getLessThanOrEqualToStartDateTime())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(damageRoot.get("startDateTime"),
                            damageCriteria.getLessThanOrEqualToStartDateTime())
            );
        }


        if (Objects.nonNull(damageCriteria.getGreaterThanOrEqualToEndDateTime())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(damageRoot.get("endDateTime"),
                            damageCriteria.getGreaterThanOrEqualToEndDateTime())
            );
        }


        if (Objects.nonNull(damageCriteria.getLessThanOrEqualToEndDateTime())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(damageRoot.get("endDateTime"),
                            damageCriteria.getLessThanOrEqualToEndDateTime())
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


    }


    private long getDamagesCount(Predicate predicate) {

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Damage> countRoot = countQuery.from(Damage.class);

        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(DamagePage damagePage) {

        Sort sort = Sort.by(damagePage.getSortDirection(), damagePage.getSortBy());
        return PageRequest.of(damagePage.getPageNumber(), damagePage.getPageSize(), sort);
    }

    private void setOrder(DamagePage damagePage, CriteriaQuery<Damage> criteriaQuery, Root<Damage> damageRoot) {

        if (damagePage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(damageRoot.get(damagePage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(damageRoot.get(damagePage.getSortBy())));
        }
    }


}
