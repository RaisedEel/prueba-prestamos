package com.banregio.prestamos.web;

import com.banregio.prestamos.dto.PagoPendiente;
import com.banregio.prestamos.dto.RequestPago;
import com.banregio.prestamos.service.PagosPendientesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@RestController
public class PagosPendientesController {

  PagosPendientesService pagosPendientesService;

  @PostMapping("/pagos")
  public List<PagoPendiente> getPagosPendientesHandler(@RequestBody RequestPago requestPago) throws ParseException {
    return pagosPendientesService.getPagosPendientesByRequest(requestPago);
  }
}
