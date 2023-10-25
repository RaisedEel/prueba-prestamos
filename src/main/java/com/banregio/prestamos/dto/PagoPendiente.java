package com.banregio.prestamos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoPendiente {
  private String cliente;
  private long plazo;
  private double tasa;
  private double monto;
  private double interes;
  private double iva;
  private double pago;
}
