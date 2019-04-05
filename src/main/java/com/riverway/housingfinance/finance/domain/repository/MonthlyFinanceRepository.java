package com.riverway.housingfinance.finance.domain.repository;

import com.riverway.housingfinance.finance.domain.MonthlyFinanceSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyFinanceRepository extends JpaRepository<MonthlyFinanceSupply, Long> {
}
