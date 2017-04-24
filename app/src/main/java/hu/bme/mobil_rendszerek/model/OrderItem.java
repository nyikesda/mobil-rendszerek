package hu.bme.mobil_rendszerek.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class OrderItem extends SugarRecord implements Serializable {
  
  @SerializedName("date")
  private Date date = null;
  
  @SerializedName("cost")
  private Integer cost = null;
  
  @SerializedName("count")
  private Integer count = null;
  
  @SerializedName("productName")
  private String productName = null;
  
  @SerializedName("orderItemId")
  private Integer orderItemId = null;
  
  @SerializedName("departmentId")
  private Integer departmentId = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getCost() {
    return cost;
  }
  public void setCost(Integer cost) {
    this.cost = cost;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getCount() {
    return count;
  }
  public void setCount(Integer count) {
    this.count = count;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getProductName() {
    return productName;
  }
  public void setProductName(String productName) {
    this.productName = productName;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getOrderItemId() {
    return orderItemId;
  }
  public void setOrderItemId(Integer orderItemId) {
    this.orderItemId = orderItemId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getDepartmentId() {
    return departmentId;
  }
  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

}
