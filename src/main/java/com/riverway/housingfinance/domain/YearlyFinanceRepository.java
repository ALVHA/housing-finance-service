package com.riverway.housingfinance.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlyFinanceRepository extends JpaRepository<YearlyFinance, Long> {
}
