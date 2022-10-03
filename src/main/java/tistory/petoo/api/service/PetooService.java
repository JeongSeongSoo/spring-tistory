package tistory.petoo.api.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import tistory.petoo.api.mapper.PetooMapper;
import tistory.petoo.common.FFMPEG;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetooService {

    private final PetooMapper petooMapper;
    private String path = "Z:\\Context\\tip";

    public PetooService(PetooMapper petooMapper) {
        this.petooMapper = petooMapper;
    }

    @Bean(initMethod = "ffmpegInit")
    public void loadContentsAndItemId() throws Exception {
        List<Map> list = petooMapper.loadContentsAndItemId();

        for (Map map : list) {
            // FFMPEG.QUALITY(path + map.get("FILE_PATH") + map.get("FILE_NAME"), 480);
            String test = "D:\\ffmpeg\\1.mp4";
            String test2 = path + map.get("FILE_PATH") + map.get("FILE_NAME");
            FFMPEG.QUALITY(test, 480);

            System.out.println(path + map.get("FILE_PATH") + map.get("FILE_NAME"));

            break;
        }
    }

}