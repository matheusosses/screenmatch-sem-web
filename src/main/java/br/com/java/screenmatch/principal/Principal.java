package br.com.java.screenmatch.principal;

import br.com.java.screenmatch.model.DadosSerie;
import br.com.java.screenmatch.model.DadosTemporada;
import br.com.java.screenmatch.model.Episodio;
import br.com.java.screenmatch.model.Serie;
import br.com.java.screenmatch.repository.SerieRepository;
import br.com.java.screenmatch.service.ConsumoApi;
import br.com.java.screenmatch.service.Conversor;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    
    private final Scanner sc = new Scanner(System.in);
    private final ConsumoApi CONSUMO = new ConsumoApi();
    private final Conversor CONVERSOR = new Conversor();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + System.getenv("OMDB_KEY");
    private SerieRepository repositorio;
    private List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        int opcao = -1;
        while(opcao != 0) {
            String menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar series pesquisadas
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        repositorio.save(new Serie(dados));
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        String nomeSerie = sc.nextLine();
        String json = CONSUMO.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        return CONVERSOR.obterDados(json, DadosSerie.class);
    }

    private void buscarEpisodioPorSerie(){
        System.out.println("Escolha uma serie pelo nome: ");
        String nomeSerie = sc.nextLine();

        listarSeriesBuscadas();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();

        if(serie.isPresent()){
            List<DadosTemporada> temporadas = new ArrayList<>();
            Serie serieEncontrada = serie.get();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                String json = CONSUMO.obterDados(ENDERECO + serieEncontrada
                        .getTitulo()
                        .replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = CONVERSOR.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(e -> new Episodio(e.numero(), e)))
                    .toList();
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }
        else System.out.println("Serie nao encontrada");
    }

    private void listarSeriesBuscadas(){
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}
