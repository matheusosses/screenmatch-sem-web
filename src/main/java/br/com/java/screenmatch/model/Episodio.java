package br.com.java.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer temporada;
    private String titulo;
    private Integer numero;
    private double avaliacao;
    private LocalDate dataLancamento;
    @ManyToOne
    private Serie serie;

    public Episodio(Integer temporada, DadosEpisodio d) {
        this.temporada = temporada;
        this.titulo = d.titulo();
        this.numero = d.numero();
        try{
            this.avaliacao = Double.parseDouble(d.avaliacao());
            this.dataLancamento = LocalDate.parse(d.dataLancamento());
        } catch(NumberFormatException e){
            this.avaliacao = 0.0;
        } catch(DateTimeParseException e){
            this.dataLancamento = null;
        }

    }

    public Episodio() {

    }

    public Integer getTemporada() {
        return temporada;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "Episodio " +
                numero +
                " temporada " + temporada +
                ", " + titulo +
                ", avaliacao = " + avaliacao +
                ", lançado em " + dataLancamento;
    }
}
