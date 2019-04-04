package com.riverway.housingfinance.finance.domain.repository;

import com.riverway.housingfinance.finance.domain.YearlyFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlyFinanceRepository extends JpaRepository<YearlyFinance, Long> {
}
