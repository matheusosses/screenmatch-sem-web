package br.com.java.screenmatch.model;

import br.com.java.screenmatch.service.traducao.ConsultaMyMemory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private String sinopse;
    private String poster;
    private String atores;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();

    public Serie(){}

    public Serie(DadosSerie dados){
        this.titulo = dados.titulo();
        this.totalTemporadas = dados.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.parseDouble(dados.avaliacao())).orElse(0.0);
        this.sinopse = ConsultaMyMemory.obterTraducao(dados.sinopse().trim());
        this.poster = dados.poster();
        this.atores = dados.atores();
        this.genero = Categoria.fromString(dados.genero().split(",")[0].trim());
    }

    public Categoria getGenero() {
        return genero;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", sinopse='" + sinopse + '\'' +
                ", poster='" + poster + '\'' +
                ", atores='" + atores + '\'' +
                ", genero=" + genero +
                '}';
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }
}
