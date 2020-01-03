package cc.mrbird.febs.wx.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * describe: 响应类
 *         2017/7/17 17:44
 * @since 0.1.0
 */
@ApiModel("通用响应体")
public class BaseRestResponse<T> {

	@ApiModelProperty("响应状态码")
	private int status = RestResponseStatus.OK;
	
	/** 响应内容*/
	@ApiModelProperty("响应内容")
	private T data;
	
	/** 响应消息*/
	@ApiModelProperty("响应消息")
	private String message = "操作成功";

	public BaseRestResponse(){
		
	}

	public BaseRestResponse<T> status(int status){
		this.status = status;
		return this;
	}

    public BaseRestResponse<T> data(T content){
        this.data = content;
        return this;
    }

    public BaseRestResponse<T> message(String message){
        this.message = message;
        return this;
    }


	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
