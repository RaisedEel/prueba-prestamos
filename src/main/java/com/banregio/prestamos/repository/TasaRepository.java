package com.banregio.prestamos.repository;

import com.banregio.prestamos.entity.Tasa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TasaRepository extends CrudRepository<Tasa, Long> {
  @Query("select a from Tasa a where a.plazoMin >= :plazo and a.plazoMax <= :plazo")
  Optional<Tasa> findByPlazo(long plazo);
}
