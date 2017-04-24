package hu.bme.mobil_rendszerek.network;

import java.util.List;

import hu.bme.mobil_rendszerek.model.Credential;
import hu.bme.mobil_rendszerek.model.Department;
import hu.bme.mobil_rendszerek.model.OrderItem;
import hu.bme.mobil_rendszerek.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface API {

    /**
     * @param userIds
     * @param credential
     * @param data
     * @return Call<Void>
     */

    @POST("Departments")
    Call<Void> departmentCreate(
            @Query("userIds") List<Integer> userIds, @Query("credential") String credential, @Body Department data
    );


    /**
   * 
   * 
   * @param credentials 
   * @return Call<User>
   */
  
  @POST("Login")
  Call<User> login(
    @Body Credential credentials
  );

  
  /**
   * 
   * 
   * @param credential 
   * @return Call<List<OrderItem>>
   */
  
  @GET("OrderItems")
  Call<List<OrderItem>> orderItemFindByDepartmentId(
    @Query("credential") String credential
  );

  
  /**
   * 
   *
   * @param credential 
   * @param data 
   * @return Call<Void>
   */
  
  @PUT("OrderItems")
  Call<OrderItem> orderItemModify(
          @Query("credential") String credential, @Body OrderItem data
  );

  
  /**
   * 
   * 
   * @param data 
   * @param credential 
   * @return Call<OrderItem>
   */
  
  @POST("OrderItems")
  Call<OrderItem> orderItemCreate(
    @Body OrderItem data, @Query("credential") String credential
  );

  
  /**
   * 
   * 
   * @param orderItemId 
   * @param credential 
   * @return Call<Void>
   */
  
  @DELETE("OrderItems")
  Call<Void> orderItemDelete(
    @Query("orderItemId") Integer orderItemId, @Query("credential") String credential
  );

  
  /**
   * 
   * 
   * @param credential 
   * @return Call<List<User>>
   */
  
  @GET("Users")
  Call<List<User>> usersFindByDepartmentId(
    @Query("credential") String credential
  );


}
