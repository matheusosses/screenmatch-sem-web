package br.com.java.screenmatch;

import br.com.java.screenmatch.principal.Principal;
import br.com.java.screenmatch.repository.SerieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	private final SerieRepository repositorio;

	public ScreenmatchApplication(SerieRepository repositorio) {
		this.repositorio = repositorio;
	}

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
		principal.exibeMenu();
	}
}
