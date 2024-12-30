package br.com.java.screenmatch;

import br.com.java.screenmatch.model.DadosSerie;
import br.com.java.screenmatch.service.ConsumoApi;
import br.com.java.screenmatch.service.Conversor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoApi consumo = new ConsumoApi();
		Conversor conversor = new Conversor();

		String json = consumo.obterDados("https://www.omdbapi.com/?t=breaking-bad&apikey=828a5de2");
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}
}
