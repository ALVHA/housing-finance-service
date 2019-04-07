package com.riverway.housingfinance.finance.domain.repository;

import com.riverway.housingfinance.finance.domain.YearlyFinanceSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YearlyFinanceRepository extends JpaRepository<YearlyFinanceSupply, Long> {

    @Query("SELECT y FROM YearlyFinanceSupply y JOIN FETCH y.bank WHERE y.housingFinance.id = :id ORDER BY y.year, y.bank.instituteCode")
    List<YearlyFinanceSupply> findYearlyFinances(@Param("id") Integer housingFinanceId);

    @Query("SELECT y " +
            "FROM YearlyFinanceSupply y JOIN FETCH y.bank " +
            "WHERE y.housingFinance.id = :id AND y.amount = (SELECT MAX(amount) FROM y)")
    Optional<YearlyFinanceSupply> findLargestOfAll(@Param("id") Integer housingFinanceId);

    @Query("SELECT y " +
            "FROM YearlyFinanceSupply y JOIN FETCH y.bank " +
            "WHERE y.housingFinance.id = :id AND y.bank.instituteCode = :bankId " +
            "AND y.amount = (SELECT MIN(amount) FROM y WHERE y.bank.instituteCode = :bankId and YEAR <= 2016)")
    YearlyFinanceSupply findMinAmountOfExchange(@Param("id") Integer housingFinanceId, @Param("bankId") String bankId);

    @Query("SELECT y " +
            "FROM YearlyFinanceSupply y JOIN FETCH y.bank " +
            "WHERE y.housingFinance.id = :id AND y.bank.instituteCode = :bankId " +
            "AND y.amount = (SELECT MAX(amount) FROM y WHERE y.bank.instituteCode = :bankId and YEAR <= 2016)")
    YearlyFinanceSupply findMaxAmountOfExchange(@Param("id") Integer housingFinanceId, @Param("bankId") String bankId);
}
