package hu.bme.mobil_rendszerek.mock_network;

import com.google.gson.Gson;

import java.io.IOException;

import hu.bme.mobil_rendszerek.model.Credential;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by nyikes on 2017. 04. 22..
 */

public class FakeInterceptor implements Interceptor {
    private MockData mockData;
    private Gson gson;

    public FakeInterceptor() {
        mockData = new MockData();
        gson = new Gson();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response.Builder b = new Response.Builder()
                .protocol(Protocol.HTTP_1_0)
                .addHeader("content-type", "application/json")
                .request(chain.request());
        switch (chain.request().url().encodedPath()) {
            case "/api/Login":
                String user = mockData.Login(gson.fromJson(chain.request().body().toString(), Credential.class));
                return new Response.Builder()
                        .code(user.equals("") ? 401 : 200)
                        .message(user)
                        .protocol(Protocol.HTTP_1_0)
                        .addHeader("content-type", "application/json")
                        .body(ResponseBody.create(MediaType.parse("application/json"), user.getBytes()))
                        .request(chain.request())
                        .build();
            default:
                throw new IllegalArgumentException("Invalid path" + chain.request().url().encodedPath());
        }
    }
}
