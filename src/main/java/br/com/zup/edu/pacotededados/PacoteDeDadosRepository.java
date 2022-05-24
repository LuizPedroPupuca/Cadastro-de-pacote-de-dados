package br.com.zup.edu.pacotededados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacoteDeDadosRepository extends JpaRepository<PacoteDeDados, Long> {
    boolean existsByHashCpf(String hashCpf);
}
