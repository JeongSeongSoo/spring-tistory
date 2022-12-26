package tistory.petoo.config.filter.wrapper;

import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class XSSFilterWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream byteArrayOutputStream;
    private ServletOutputStream servletOutputStream;

    public XSSFilterWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (servletOutputStream == null) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            servletOutputStream = new ServletOutputStream() {

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setWriteListener(WriteListener listener) {

                }

                @Override
                public void write(int b) throws IOException {
                    dataOutputStream.write(b);
                }
            };
        }

        return servletOutputStream;
    }

    public byte[] replaceXSS() {
        // 2022.12.26[프뚜]: XSS 처리
        return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8)
                .replaceAll("\\<", "&lt;")
                .replaceAll("\\>", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#39;")
                .getBytes(StandardCharsets.UTF_8);
    }

}