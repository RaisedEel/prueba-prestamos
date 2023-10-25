package com.banregio.prestamos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tasa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idKey;

  private int plazoMin;
  private int plazoMax;
  private String interes;
}
