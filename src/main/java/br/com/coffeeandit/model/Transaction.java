package br.com.coffeeandit.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction implements Serializable {
    private BigDecimal valor;

    private String tipoChave;

    private String chave;

    private String linha;

    private StatusPix status;

    private Long agencia;

    private Long conta;

    private LocalDateTime data;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipoChave() {
        return tipoChave;
    }

    public void setTipoChave(String tipoChave) {
        this.tipoChave = tipoChave;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public StatusPix getStatus() {
        return status;
    }

    public void setStatus(StatusPix status) {
        this.status = status;
    }

    public Long getAgencia() {
        return agencia;
    }

    public void setAgencia(Long agencia) {
        this.agencia = agencia;
    }

    public Long getConta() {
        return conta;
    }

    public void setConta(Long conta) {
        this.conta = conta;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }


    public void analisada() {
        setStatus(StatusPix.APPROVED);
    }

    public void suspeitaFraude() {
        setStatus(StatusPix.REPROVED);
    }

    public void analiseHumana() {
        setStatus(StatusPix.IN_PROCESS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(valor, that.valor) && Objects.equals(chave, that.chave) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor, chave, data);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "valor=" + valor +
                ", chave='" + chave + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}

