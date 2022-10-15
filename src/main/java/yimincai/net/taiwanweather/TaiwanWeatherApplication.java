package yimincai.net.taiwanweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 告知 Spring boot 此 class 為 Spring boot 的進入點
@SpringBootApplication
public class TaiwanWeatherApplication {

	// Spring boot 的程式進入點 通常不會對這個『檔案』做任何更動
	public static void main(String[] args) {
		SpringApplication.run(TaiwanWeatherApplication.class, args);
	}

}
