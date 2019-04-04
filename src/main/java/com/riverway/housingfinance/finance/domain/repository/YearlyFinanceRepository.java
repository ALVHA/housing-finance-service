package com.riverway.housingfinance.finance.domain.repository;

import com.riverway.housingfinance.finance.domain.YearlyFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearlyFinanceRepository extends JpaRepository<YearlyFinance, Long> {

    @Query("SELECT y FROM YearlyFinance y JOIN FETCH y.bank ORDER BY y.year, y.bank.instituteCode")
    List<YearlyFinance> findYear();
}
