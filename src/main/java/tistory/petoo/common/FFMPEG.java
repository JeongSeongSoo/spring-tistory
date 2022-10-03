package tistory.petoo.common;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class FFMPEG {

    public static Map<Integer, Map<String, Integer>> QUALITY_MAP;
    public static CloudBlobContainer CLOUD_BLOB_CONTAINER;
    private static FFmpeg FFMPEG;
    private static FFprobe FFPROBE;

    @PostConstruct
    private void ffmpegInit() throws Exception {
        // 2022.08.24[프뚜]: ffmpeg.exe 경로 지정
        FFMPEG = new FFmpeg("D:\\ffmpeg\\bin\\ffmpeg");

        // 2022.08.24[프뚜]: ffprobe.exe 경로 지정
        FFPROBE = new FFprobe("D:\\ffmpeg\\bin\\ffprobe");

        Map<String, Integer> map480 = new HashMap();
        map480.put("width", 854);
        map480.put("height", 480);

        Map<String, Integer> map720 = new HashMap();
        map720.put("width", 1280);
        map720.put("height", 720);

        Map<String, Integer> map1080 = new HashMap();
        map1080.put("width", 1920);
        map1080.put("height", 1080);

        QUALITY_MAP = new HashMap();
        QUALITY_MAP.put(480, map480);
        QUALITY_MAP.put(720, map720);
        QUALITY_MAP.put(1080, map1080);

        CLOUD_BLOB_CONTAINER = AZURE_BLOB_CONNECT();
    }

    public static void BLOB_UPLOAD(String fullPath) throws Exception {
        new Thread(() -> {
            try {
                System.out.println("업로드 시작 > " + fullPath);

                // 2022.09.27[프뚜]: 컨테이너에 저장 > 경로 + 파일명, 타입 지정
                CloudBlockBlob blob = CLOUD_BLOB_CONTAINER.getBlockBlobReference("example/480/" + FilenameUtils.getName(fullPath));

                if ("ts".equals(FilenameUtils.getExtension(fullPath))) {
                    blob.getProperties().setContentType("video/MP2T");
                } else {
                    blob.getProperties().setContentType("application/x-mpegURL");
                }

                // 2022.09.27[프뚜]: blob 업로드 실행
                blob.uploadFromFile(fullPath);

                System.out.println("업로드 종료 > " + fullPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void QUALITY(String fullPath, Integer quality) throws Exception {
        // 2022.08.24[프뚜]: 동영상 파일(mp4) 경로
        String newFileName = FilenameUtils.getBaseName(fullPath) + "_" + QUALITY_MAP.get(quality).get("height") + "p";
        String newFilePath = FilenameUtils.getFullPath(fullPath) + quality + "\\";

        Path path = Paths.get(newFilePath);

        if (!path.toFile().isFile()) {
            Files.createDirectories(path);
        }

        String newFileFullPath = path + "\\" + newFileName + ".m3u8";

        System.out.println("파일 생성 > " + newFileFullPath);

//        FFmpegBuilder builder = new FFmpegBuilder()
//                .setInput(fullPath)
//                .overrideOutputFiles(true)
//                .addOutput(newFileFullPath)
//                .setVideoWidth(QUALITY_MAP.get(quality).get("width"))
//                .setVideoHeight(QUALITY_MAP.get(quality).get("height"))
//                .done();

        FFmpegBuilder builder = new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(fullPath)
                .addOutput(newFileFullPath)
                .setVideoWidth(QUALITY_MAP.get(quality).get("width"))
                .setVideoHeight(QUALITY_MAP.get(quality).get("height"))
                .addExtraArgs("-profile:v", "baseline")
                .addExtraArgs("-level", "3.0")
                .addExtraArgs("-start_number", "0")
                .addExtraArgs("-hls_time", "10")
                .addExtraArgs("-hls_list_size", "0")
                .addExtraArgs("-f", "hls")
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(FFMPEG, FFPROBE);
        executor.createJob(builder).run();

        File files = new File(FilenameUtils.getFullPath(newFilePath));
        for (File file : files.listFiles()) {
            if (file.getName().indexOf(newFileName) > -1) {
                BLOB_UPLOAD(file.getAbsolutePath());
            }
        }
    }

    private static CloudBlobContainer AZURE_BLOB_CONNECT() throws Exception {
        // 2022.09.27[프뚜]: 스토어 연결을 위해 인증
        String storageConnectionString = "DefaultEndpointsProtocol=https;" +
                "AccountName=;" +
                "AccountKey=";

        // 2022.09.27[프뚜]: 스토리지 계정 > 데이터 스토리지 > 컨테이너 연결
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

        // 2022.09.27[프뚜]: 컨테이너명
        CloudBlobContainer container = blobClient.getContainerReference("contents");
        container.createIfNotExists(new BlobRequestOptions(), new OperationContext());

        return container;
    }

}
