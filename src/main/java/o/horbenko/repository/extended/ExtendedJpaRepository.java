package o.horbenko.repository.extended;

import o.horbenko.repository.extended.containers.SpecificationAndSorting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface ExtendedJpaRepository<DomainType, IdType extends Serializable>
    extends JpaRepository<DomainType, IdType>, JpaSpecificationExecutor<DomainType> {


    default Page<DomainType> fetchPageWith(SpecificationAndSorting<DomainType> specificationAndSorting,
                                           int pageNum, int countOnPage) {

        Specification<DomainType> filterSpecification = specificationAndSorting.getSpecification();
        Sort sort = specificationAndSorting.getSorting();

        PageRequest pageRequest = new PageRequest(pageNum, countOnPage, sort);

        return this.findAll(filterSpecification, pageRequest);
    }

}
