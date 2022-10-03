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
		String absolutePath = "D:\\ssjeong\\프뚜.png";
		String name = FilenameUtils.getName(absolutePath);
		String fullPath = FilenameUtils.getFullPath(absolutePath);
		String baseName = FilenameUtils.getBaseName(absolutePath);
		String extension = FilenameUtils.getExtension(absolutePath);

		// 2022.10.03[프뚜]: 확장자를 제거한 순수 파일명
		System.out.println("[프뚜] > 01. 파일명: " + baseName);

		// 2022.10.03[프뚜]: 파일명을 제거한 순수 확장자
		System.out.println("[프뚜] > 02. 확장자: " + extension);

		// 2022.10.03[프뚜]: 파일이 있는 경로
		System.out.println("[프뚜] > 03. 파일경로: " + fullPath);

		// 2022.10.03[프뚜]: 파일의 이름과 확장자
		System.out.println("[프뚜] > 04. 파일명 + 확장자: " + name);
	}

}