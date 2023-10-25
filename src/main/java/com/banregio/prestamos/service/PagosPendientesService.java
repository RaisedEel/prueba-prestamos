package com.banregio.prestamos.service;

import com.banregio.prestamos.dto.PagoPendiente;
import com.banregio.prestamos.dto.RequestPago;
import com.banregio.prestamos.entity.Prestamo;
import com.banregio.prestamos.entity.Tasa;
import com.banregio.prestamos.repository.PrestamoRepository;
import com.banregio.prestamos.repository.TasaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PagosPendientesService {

  PrestamoRepository prestamoRepository;
  TasaRepository tasaRepository;

  public List<PagoPendiente> getPagosPendientesByRequest(RequestPago request) {
    List<Prestamo> prestamos = prestamoRepository
        .findAllByClienteAndPendiente(request.getCliente());

    // Se crea un formateador para cadenas con el formato dd-MM-yyyy
    // Las cadenas aceptadas son del tipo 31-12-2022 (31 de diciembre del 2022)
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    ArrayList<PagoPendiente> pagosPendientes = new ArrayList<>();
    for (Prestamo prestamo : prestamos) {
      // Convierte las cadenas dadas por el usuario en objetos LocalDate
      LocalDate inicio = LocalDate.parse(prestamo.getFecha(), dateFormat); // Inicio del préstamo
      LocalDate actual = LocalDate.parse(request.getFechaActual(), dateFormat); // Fecha final para el préstamo

      // Se utiliza el ChronoUnit DAYS para calcular la diferencia en días
      long plazo = ChronoUnit.DAYS.between(inicio, actual);
      // Se obtiene la tasa que corresponde al periodo de días calculado
      Tasa tasa = tasaRepository.findByPlazo(plazo).orElse(new Tasa(null, 31, 360, "5.00%"));

      // Los porcentajes se convierten en doubles. Los porcentajes vienen como una cadena del estilo "10%"
      double tasaInteres = Double.parseDouble(tasa.getInteres().substring(0, tasa.getInteres().length() - 1)) / 100.0;
      double tasaIva = Double.parseDouble(request.getIva().substring(0, request.getIva().length() - 1)) / 100.0;

      // Calculamos el valor del interés mediante la función: monto * interés * (días / año comercial)
      double interes = prestamo.getMonto() * tasaInteres * (1.0 * plazo / request.getAnoComercial());
      // Calculamos el valor del IVA para el préstamo
      double iva = interes * tasaIva;
      // Finalmente, calculamos el pago completo a realizar para liquidar el préstamo (redondeamos a 2 decimales)
      double pago = Math.round((prestamo.getMonto() + interes + iva) * 100) / 100.0;

      pagosPendientes.add(
          new PagoPendiente(
              request.getCliente(),
              plazo,
              tasaInteres,
              prestamo.getMonto(),
              Math.round(interes * 100) / 100.0,
              Math.round(iva * 100) / 100.0,
              pago
          )
      );
    }

    return pagosPendientes;
  }

}
