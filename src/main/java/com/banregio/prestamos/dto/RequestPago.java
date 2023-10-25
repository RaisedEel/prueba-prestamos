package com.banregio.prestamos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPago {
  private String fechaActual;
  private String cliente;
  private String iva;
  private int anoComercial;
}
