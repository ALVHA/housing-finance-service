package com.riverway.housingfinance.finance.domain.repository;

import com.riverway.housingfinance.finance.domain.MonthlyFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyFinanceRepository extends JpaRepository<MonthlyFinance, Long> {
}
