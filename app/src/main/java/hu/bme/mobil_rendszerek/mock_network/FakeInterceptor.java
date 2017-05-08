package hu.bme.mobil_rendszerek.mock_network;

import com.google.gson.Gson;

import java.io.IOException;

import hu.bme.mobil_rendszerek.model.Credential;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

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
        Buffer buffer;
        Response.Builder b = new Response.Builder()
                .protocol(Protocol.HTTP_1_0)
                .addHeader("content-type", "application/json")
                .request(chain.request());
        switch (chain.request().url().encodedPath()) {
            case "/api/Login":
                buffer = new Buffer();
                chain.request().body().writeTo(buffer);
                String user = mockData.Login(gson.fromJson(buffer.readUtf8(), Credential.class));
                return new Response.Builder()
                        .code(user.equals("") ? 401 : 200)
                        .message(user)
                        .protocol(Protocol.HTTP_1_0)
                        .addHeader("content-type", "application/json")
                        .body(ResponseBody.create(MediaType.parse("application/json"), user.getBytes()))
                        .request(chain.request())
                        .build();
            case "/api/OrderItems":
                if (chain.request().method().equals("POST") || chain.request().method().equals("PUT")) {
                    buffer = new Buffer();
                    chain.request().body().writeTo(buffer);
                    String orderItem = buffer.readUtf8();
                    return new Response.Builder()
                            .code(200)
                            .message(orderItem)
                            .protocol(Protocol.HTTP_1_0)
                            .addHeader("content-type", "application/json")
                            .body(ResponseBody.create(MediaType.parse("application/json"), orderItem.getBytes()))
                            .request(chain.request())
                            .build();
                }
                return new Response.Builder()
                        .code(200)
                        .message("{}")
                        .protocol(Protocol.HTTP_1_0)
                        .addHeader("content-type", "application/json")
                        .body(ResponseBody.create(MediaType.parse("application/json"), "{}".getBytes()))
                        .request(chain.request())
                        .build();
            case "/api/Departments":
                return new Response.Builder()
                        .code(200)
                        .message("{}")
                        .protocol(Protocol.HTTP_1_0)
                        .addHeader("content-type", "application/json")
                        .body(ResponseBody.create(MediaType.parse("application/json"), "{}".getBytes()))
                        .request(chain.request())
                        .build();
            default:
                throw new IllegalArgumentException("Invalid path" + chain.request().url().encodedPath());
        }
    }
}
