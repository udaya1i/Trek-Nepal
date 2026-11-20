package com.trek.core.admin.specification;

import com.trek.core.entity.Admin;
import com.trek.core.repository.AdminRepository;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.specification.FilterFieldToEntityFieldMapper;
import com.trek.core.specification.SpecificationUtil;
import com.trek.core.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminSpecification {

    /**
     * @see https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html
     */
    private final AdminRepository adminRepository;


    // Fetch Admins using custom specification builder.
    public Page<Admin> getAll(FilterRequest params) {
        Specification<Admin> specification = SpecificationUtil.getSpecification(FilterFieldToEntityFieldMapper.map(params.getFilterCriteriaRequests(), Admin.class), Admin.class);
        return adminRepository.findAll(specification, PaginationUtil.of(params.getPaginationRequest()));
    }

    public Specification<Admin> byPhoneNumberSpec(String phoneNumber) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber));
    }

    public Specification<Admin> byEmailSpec(String email) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email));
    }
}
