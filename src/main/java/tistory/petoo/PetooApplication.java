package tistory.petoo;

import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetooApplication {

	public static void main(String[] args) throws Exception {
		// SpringApplication.run(PetooApplication.class, args);
		FILE();
	}

	private static void FILE() throws Exception {
		String fullFilePath = "D:\\Doc\\ssjeong\\프뚜.png";
		FilenameUtils.getExtension("");
		System.out.println("[프뚜] > ");
	}

}