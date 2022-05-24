package br.com.zup.edu.pacotededados;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;

public class PacoteDeDadosRequest {

    @NotBlank
    @CPF
    private String cpf;

    @NotNull
    @Min(6)
    @Max(49)
    private Integer quantidadeDados;

    @NotBlank
    @Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}")
    private String celular;

    public PacoteDeDadosRequest(String cpf, Integer quantidadeDados, String celular) {
        this.cpf = cpf;
        this.quantidadeDados = quantidadeDados;
        this.celular = celular;
    }

    public PacoteDeDadosRequest() {
    }

    public String getCpf() {
        return cpf;
    }

    public Integer getQuantidadeDados() {
        return quantidadeDados;
    }

    public String getCelular() {
        return celular;
    }

    public PacoteDeDados toModel(){
        return new PacoteDeDados(cpf, quantidadeDados, celular);
    }
}
