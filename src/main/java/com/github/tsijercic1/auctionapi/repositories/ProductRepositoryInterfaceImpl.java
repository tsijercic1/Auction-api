package com.github.tsijercic1.auctionapi.repositories;

import com.github.tsijercic1.auctionapi.models.Product;
import com.github.tsijercic1.auctionapi.request.FilterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class ProductRepositoryInterfaceImpl implements ProductRepositoryInterface {
    private final EntityManager entityManager;

    @Override
    public List<Product> findByFilter(FilterRequest filterRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = criteriaBuilder.createQuery(Product.class);

        Root<Product> root = cq.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();

        if(filterRequest.getMinPrice() != null && filterRequest.getMinPrice().compareTo(0) >= 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startPrice"), filterRequest.getMinPrice()));
        }

        if(filterRequest.getMaxPrice() != null && filterRequest.getMaxPrice().compareTo(0) >= 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startPrice"), filterRequest.getMaxPrice()));
        }

        if(filterRequest.getSearch() != null && !filterRequest.getSearch().isEmpty()) {
            String pattern = String.format("%%%s%%", filterRequest.getSearch());
            predicates.add(criteriaBuilder.like(root.get("name"), pattern));
        }

        if (filterRequest.getCategory() != null) {
            predicates.add(criteriaBuilder.like(root.get("subcategory").get("category").get("name"), filterRequest.getCategory()));

            if(filterRequest.getSubcategory() != null) {
                predicates.add(criteriaBuilder.like(root.get("subcategory").get("name"), filterRequest.getSubcategory()));
            }
        }

        try {
            if(filterRequest.getOrderBy() != null) {
                Expression<Object> expression = root.get(filterRequest.getOrderBy());
                boolean isDescending = filterRequest.getDescending() != null && filterRequest.getDescending();
                cq.orderBy(isDescending ? criteriaBuilder.desc(expression) : criteriaBuilder.asc(expression));
            }
        } catch(IllegalArgumentException ignore) {}

        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Product> typedQuery = entityManager.createQuery(cq);
        int page = 0, pageSize = 9;
        if (filterRequest.getPage() != null && filterRequest.getPage() >= 0) {
            page = filterRequest.getPage();
        }
        if (filterRequest.getPageSize() != null && filterRequest.getPageSize() > 0) {
            pageSize = filterRequest.getPageSize();
        }
        typedQuery.setFirstResult(page * pageSize);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }
}
