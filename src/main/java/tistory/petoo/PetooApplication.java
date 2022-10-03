package tistory.petoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetooApplication {

	public static void main(String[] args) throws Exception {
//		for (Map.Entry<Integer, Map<String, Integer>> map : qualityMap.entrySet()) {
//			new Thread(() -> {
//				try {
//					FFMPEG(container, map.getValue());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}).start();
//		}

		SpringApplication.run(PetooApplication.class, args);
	}

}