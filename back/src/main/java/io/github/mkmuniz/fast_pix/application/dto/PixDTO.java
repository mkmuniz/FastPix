package io.github.mkmuniz.fast_pix.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PixDTO {
    private Long id;
    private String pixKey;
    private BigDecimal value;
    private String name;
    private String city;
    private String state;
}
