package tistory.petoo;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetooApplication {

	public static void main(String[] args) {
		ffmpegRun();
		SpringApplication.run(PetooApplication.class, args);
	}

	private static void ffmpegRun() {
		try {
			// 2022.08.24[프뚜]: 동영상 변환 시작 시간
			long beforeTime = System.currentTimeMillis();

			// 2022.08.24[프뚜]: ffmpeg.exe 경로 지정
			FFmpeg ffmpeg = new FFmpeg("D:\\ffmpeg\\bin\\ffmpeg");

			// 2022.08.24[프뚜]: ffprobe.exe 경로 지정
			FFprobe ffprobe = new FFprobe("D:\\ffmpeg\\bin\\ffprobe");

			// 2022.08.24[프뚜]: 동영상 파일(mp4) 경로
			String originFilePath = "D:\\ffmpeg\\";

			FFmpegBuilder builder = new FFmpegBuilder()
				.setInput(originFilePath + "1.mp4") // 2022.08.24[프뚜]: 파일 경로
                .overrideOutputFiles(true) // 2022.08.24[프뚜]: 파일 덮어씌기
                .addOutput(originFilePath + "2.mp4") // 2022.08.24[프뚜]: 생성되는 파일
                .setVideoWidth(640)
                .setVideoHeight(480)
                .done();

			FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        	executor.createJob(builder).run();

			/* 2022.08.24[프뚜]: m3u8로 변환하
			FFmpegBuilder builder = new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(originFilePath + "1.mp4")
                .addOutput("D:\\ffmpeg\\test.m3u8")
                .setVideoWidth(640)
                .setVideoHeight(480)
                .addExtraArgs("-profile:v", "baseline")
                .addExtraArgs("-level", "3.0")
                .addExtraArgs("-start_number", "0")
                .addExtraArgs("-hls_time", "10")
                .addExtraArgs("-hls_list_size", "0")
                .addExtraArgs("-f", "hls")
                .done();
			*/

			// 2022.08.24[프뚜]: 동영상 변환 완료 시간
			long afterTime = System.currentTimeMillis();
			long secDiffTime = (afterTime - beforeTime) / 1000;

			System.out.println("파일 변환 시간 => " + secDiffTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}