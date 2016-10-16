package com.witty.consumer.test;

import com.google.gson.Gson;
import com.witty.error.ErrorMessage;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * AbstractMvcTest - parent for all MVC oriented tests
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles(profiles = "auto", inheritProfiles = false)
@ContextConfiguration(locations = "classpath*:META-INF/spring/mvc-test-context.xml")
public class AbstractMvcTest {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerTest.class);

    @Inject
    private WebApplicationContext wac;

    protected Gson gson = new Gson();

    private MockMvc mock;

    private Set<Integer> passCodes = new HashSet<>();

    @Before
    public void before() {
        mock = MockMvcBuilders.webAppContextSetup(wac).build();
        passCodes.add(200);
    }

    protected void applyCodes(Integer... codes) {
        passCodes.addAll(Arrays.asList(codes));
    }

    protected ResultActions perform(RequestBuilder requestBuilder) throws Exception {
        return mock.perform(requestBuilder);
    }

    protected <T> T doGet(String path, Class<T> clazz) throws Exception {
        return doGet(path, null, clazz);
    }

    protected <T> T doGet(String path, Map<String, String> params, Class<T> clazz) throws Exception {
        MockHttpServletRequestBuilder get = get(path);
        fillParams(get, params);
        return getResult(get, clazz);
    }

    protected Boolean doDelete(String path) throws Exception {
        MockHttpServletRequestBuilder delete = delete(path);
        return getResult(delete, Boolean.class);
    }

    private <T> T getResult(MockHttpServletRequestBuilder b, Class<T> clazz) throws Exception {
        MvcResult mvcResult = getMvcResult(b);
        try {
            String value = new String(mvcResult.getResponse().getContentAsByteArray(), "utf8");
            return gson.fromJson(value, clazz);
        } catch (Exception e) {
            ErrorMessage error = gson.fromJson(mvcResult.getResponse().getContentAsString(), ErrorMessage.class);
            throw new Exception(error.getErrors().get(0));
        }
    }

    private MvcResult getMvcResult(MockHttpServletRequestBuilder builder) throws Exception {
        ResultActions perform = perform(builder);
        return perform.andExpect(result -> {
            int status = result.getResponse().getStatus();
            if (!passCodes.contains(status)) {
                logger.error("Response HTTP Status = {}", status);
                throw new AssertionError();
            }
        }).andReturn();
    }

    protected <T> T doPut(String path, Map<String, String> params, Class<T> clazz) throws Exception {
        return doPut(path, params, null, clazz);
    }

    protected <T> T doPut(String path, Object body, Class<T> clazz) throws Exception {
        return doPut(path, null, body, clazz);
    }

    protected <T> T doPut(String path, Map<String, String> params, Object body, Class<T> clazz) throws Exception {
        MockHttpServletRequestBuilder put = put(path);
        fillParams(put, params);
        if (body != null) {
            put.contentType(TestUtils.APPLICATION_JSON_UTF8).content(TestUtils.convertObjectToJsonBytes(body));
        }
        return getResult(put, clazz);
    }

    protected <T> T doPost(String path, Map<String, String> params, Class<T> clazz) throws Exception {
        return doPost(path, params, null, clazz);
    }

    protected <T> T doPost(String path, Object body, Class<T> clazz) throws Exception {
        return doPost(path, null, body, clazz);
    }

    protected <T> T doPost(String path, Map<String, String> params, Object body, Class<T> clazz) throws Exception {
        MockHttpServletRequestBuilder post = post(path);
        fillParams(post, params);
        if (body != null) {
            post.contentType(TestUtils.APPLICATION_JSON_UTF8).content(TestUtils.convertObjectToJsonBytes(body));
        }

        String json = gson.toJson(body);
        logger.info("[REQUEST] " + clazz.getSimpleName() + " -> " + json);

        return getResult(post, clazz);
    }

    private static void fillParams(MockHttpServletRequestBuilder builder, Map<String, String> params) {
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.param(entry.getKey(), entry.getValue());
            }
        }
    }

}
