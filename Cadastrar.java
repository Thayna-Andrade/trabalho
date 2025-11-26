package model;

import java.time.LocalDateTime;

public class Cadastrar {
    private Long idProblema;
    private String titulo;
    private String descricao;
    private String rua;
    private String numero;
    private String bairro;
    private String fotoUrl;
    private LocalDateTime dataCriacao;
    private Integer idUsuario;
    private Integer idCidade;
    private String status;

    public Cadastrar() {
        this.dataCriacao = LocalDateTime.now();
        this.status = "Pendente";
    }

    public Cadastrar(Long idProblema, String titulo, String descricao, String rua, 
                    String numero, String bairro, String fotoUrl, LocalDateTime dataCriacao, 
                    Integer idUsuario, Integer idCidade, String status) {
        this.idProblema = idProblema;
        this.titulo = titulo;
        this.descricao = descricao;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.fotoUrl = fotoUrl;
        this.dataCriacao = dataCriacao;
        this.idUsuario = idUsuario;
        this.idCidade = idCidade;
        this.status = status;
    }

    // Getters e Setters
    public Long getIdProblema() {
        return idProblema;
    }

    public void setIdProblema(Long idProblema) {
        this.idProblema = idProblema;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Problema [id=" + idProblema + ", titulo=" + titulo + ", descricao=" + descricao + 
               ", rua=" + rua + ", numero=" + numero + ", bairro=" + bairro + 
               ", status=" + status + "]";
    }
}