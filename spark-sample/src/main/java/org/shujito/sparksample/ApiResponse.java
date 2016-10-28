package org.shujito.sparksample;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Getter;

/**
 * @author shujito, 10/28/16
 */
@Builder
public class ApiResponse {
	@Getter
	@SerializedName("success")
	private boolean success = false;
	@Getter
	@SerializedName("status")
	private int status = 500;
	@Getter
	@SerializedName("message")
	private String message;
	@Getter
	@SerializedName("data")
	private String data;
	@Getter
	@SerializedName("stack_trace")
	private String stackTrace;
}
