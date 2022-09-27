package tistory.petoo;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class PetooApplication {

	public static void main(String[] args) throws Exception {
		long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

		AZURE_BLOB_UPLOAD();

		long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
		long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
		System.out.println("시간차이(m) : "+secDiffTime);

		// SpringApplication.run(PetooApplication.class, args);
	}

	private static void AZURE_BLOB_UPLOAD() throws Exception {
		// 2022.09.27[프뚜]: 스토어 연결을 위해 인증
		String storageConnectionString =
				"DefaultEndpointsProtocol=https;" +
						"AccountName=<account-name/>;" +
						"AccountKey=<account-key/>";

		// 2022.09.27[프뚜]: 스토리지 계정 > 데이터 스토리지 > 컨테이너 연결
		CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
		CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

		// 2022.09.27[프뚜]: 컨테이너명
		CloudBlobContainer container = blobClient.getContainerReference("ssjeong");
		container.createIfNotExists(new BlobRequestOptions(), new OperationContext());

		// 2022.09.27[프뚜]: 업로드 대상
		Path path = Paths.get("D:\\Doc\\ssjeong\\test.mp4");

		// 2022.09.27[프뚜]: 컨테이너에 저장 > 경로 + 파일명, 타입 지정
		CloudBlockBlob blob = container.getBlockBlobReference("example/" + path.toFile().getName());
		blob.getProperties().setContentType("video/mp4");

		// 2022.09.27[프뚜]: blob 업로드 실행
		blob.uploadFromFile(path.toFile().getAbsolutePath());
	}

}