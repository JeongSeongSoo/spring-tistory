package tistory.petoo.runner;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.util.ContentStreamBase;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class SolrjRunner implements ApplicationRunner {

    private String[] zookeepers = {
            "192.168.0.100:7777",
            "192.168.0.101:7777",
            "192.168.0.102:7777"
    };

    private CloudSolrClient solrClient = new CloudSolrClient.Builder(
            Arrays.asList(zookeepers)
            , Optional.empty()).build();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        solrClient.setDefaultCollection("collections");

        // 2022.11.23[프뚜]: /select
        Map<String, Object> getParams = new HashMap();
        getParams.put("q", "테스트");
        getParams.put("wt", "json");
        get("/select", getParams);

        // 2022.11.23[프뚜]: /delete
        Map<String, Object> deleteParams = new HashMap();
        deleteParams.put("commit", "true");
        deleteParams.put("wt", "json");
        post("/delete", deleteParams, "application/xml", "<delete><query>*:*</query></delete>");

        // 2022.11.23[프뚜]: /tag
        Map<String, Object> tagParams = new HashMap();
        tagParams.put("commit", "true");
        tagParams.put("wt", "json");
        post("/tag", tagParams, "text/plain", "프뚜");

        // 2022.11.23[프뚜]: /update
        Map<String, Object> updateParams = new HashMap();
        updateParams.put("commit", "true");
        updateParams.put("wt", "json");
        post("/update", updateParams, "application/json", "{\"ID\": \"ssjeong\"}");
    }

    private void get(String path, Map params) throws Exception {
        SolrQuery query = new SolrQuery();

        Set<String> keys = new HashSet();
        for (String key : keys) {
            query.add(key, (String) params.get(key));
        }

        QueryRequest request = new QueryRequest(query);
        request.setMethod(SolrRequest.METHOD.GET);

        // 2022.11.23[프뚜]: http://[host]:[port]/[collections]/[path]
        request.setPath(path);

        QueryResponse response = request.process(solrClient);
        response.jsonStr();
    }

    private void post(String path, Map params, String contentType, String body) throws Exception {
        ContentStreamBase.StringStream contentStreamBase = new ContentStreamBase.StringStream(body);
        contentStreamBase.setContentType(contentType);

        ContentStreamUpdateRequest request = new ContentStreamUpdateRequest(path);
        request.addContentStream(contentStreamBase);

        Set<String> keys = new HashSet();
        for (String key : keys) {
            request.setParam(key, (String) params.get(key));
        }

        UpdateResponse response = request.process(solrClient);
        response.jsonStr();
    }

}
