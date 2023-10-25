package com.banregio.prestamos.repository;

import com.banregio.prestamos.entity.Prestamo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrestamoRepository extends CrudRepository<Prestamo, Long> {
  @Query("select p from Prestamo p where p.cliente = :cliente and p.estado = 'Pendiente'")
  List<Prestamo> findAllByClienteAndPendiente(String cliente);

  List<Prestamo> findAllByCliente(String cliente);
}
