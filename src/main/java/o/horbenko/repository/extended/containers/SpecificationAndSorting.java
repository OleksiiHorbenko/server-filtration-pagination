package o.horbenko.repository.extended.containers;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class SpecificationAndSorting<DomainType> {

    private Specification<DomainType> specification;
    private Sort sorting;

    public SpecificationAndSorting(Specification<DomainType> specification, Sort sorting) {
        this.specification = specification;
        this.sorting = sorting;
    }

    public void andSpecification(Specification<DomainType> toAppend) {
        this.specification = Specifications.where(this.specification).and(toAppend);
    }

    public void orSpecification(Specification<DomainType> toAppend) {
        this.specification = Specifications.where(this.specification).or(toAppend);
    }


    public Specification<DomainType> getSpecification() {
        return specification;
    }

    public void setSpecification(Specification<DomainType> specification) {
        this.specification = specification;
    }

    public Sort getSorting() {
        return sorting;
    }

    public void setSorting(Sort sorting) {
        this.sorting = sorting;
    }
}
