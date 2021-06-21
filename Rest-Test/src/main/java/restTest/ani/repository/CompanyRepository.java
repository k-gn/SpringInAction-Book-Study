package restTest.ani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import restTest.ani.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
