package com.jlkj.web.shopnew.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ConversionRateDto {
    int potentialUserCountResult ;
    int intentionalUserCountResult;
    int customerUserCountResult;
    BigDecimal pToi ;
    BigDecimal iToc;

}
