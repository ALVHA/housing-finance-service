package com.riverway.housingfinance.finance.domain;

import com.riverway.housingfinance.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HousingFinanceFile {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String savedName;

    @Column(nullable = false)
    private Long size;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User uploader;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "housing_finance_id")
    private List<YearlyFinanceSupply> yearlyFinanceSupplies;

    private HousingFinanceFile(String originalName, String savedName, Long size, User user, List<YearlyFinanceSupply> yearlyFinanceSupplies) {
        this.originalName = originalName;
        this.savedName = savedName.trim();
        this.size = size;
        this.uploader = user;
        this.yearlyFinanceSupplies = yearlyFinanceSupplies;
    }

    public Integer getId() {
        return id;
    }

    public static HousingFinanceFile of(MultipartFile file, User user, List<YearlyFinanceSupply> yearlyFinanceSupplies) {
        String originalName = file.getOriginalFilename();
        return new HousingFinanceFile(originalName, convert(originalName), file.getSize(), user, yearlyFinanceSupplies);
    }

    public static String convert(String file) {
        return UUID.randomUUID().toString().replace("-", "") + parseExtention(file);
    }

    public static String parseExtention(String file) {
        return file.substring(file.lastIndexOf("."));
    }
}
