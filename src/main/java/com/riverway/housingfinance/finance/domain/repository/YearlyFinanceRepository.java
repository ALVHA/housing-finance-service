package com.riverway.housingfinance.finance.domain.repository;

import com.riverway.housingfinance.finance.domain.YearlyFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YearlyFinanceRepository extends JpaRepository<YearlyFinance, Long> {

    @Query("SELECT y FROM YearlyFinance y JOIN FETCH y.bank ORDER BY y.year, y.bank.instituteCode")
    List<YearlyFinance> findYearlyFinances();

    @Query("SELECT y " +
            "FROM YearlyFinance y JOIN FETCH y.bank " +
            "WHERE y.amount = (SELECT MAX(amount) FROM y)")
    Optional<YearlyFinance> findLargestAmount();
}
