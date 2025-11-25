package com.example.JobTask.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyProductRequestDTO {

    @NotNull
    private Long id;

    @NotNull
    @PositiveOrZero
    private Long quantity;

    @NotNull
    @PositiveOrZero
    private Long moneyYouHave;
}
