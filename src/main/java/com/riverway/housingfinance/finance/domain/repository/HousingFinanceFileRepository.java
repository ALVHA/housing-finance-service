package com.riverway.housingfinance.finance.domain.repository;

import com.riverway.housingfinance.finance.domain.HousingFinanceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HousingFinanceFileRepository extends JpaRepository<HousingFinanceFile, Long> {
}
