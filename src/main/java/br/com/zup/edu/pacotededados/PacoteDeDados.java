package br.com.zup.edu.pacotededados;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class PacoteDeDados {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cpf;

    @Column(unique = true)
    private String hashCpf;

    @Column(nullable = false)
    private Integer quantidadeDados;

    @Column(nullable = false)
    private String celular;

    @Column(nullable = false)
    private LocalDate dataCadastro = LocalDate.now();

    public PacoteDeDados(String cpf, Integer quantidadeDados, String celular) {
        this.quantidadeDados = quantidadeDados;
        this.celular = celular;
        this.cpf = CPFUtils.anonymize(cpf);
        this.hashCpf = CPFUtils.hash(cpf);
    }

    public PacoteDeDados() {
    }

    public Long getId() {
        return id;
    }
}
