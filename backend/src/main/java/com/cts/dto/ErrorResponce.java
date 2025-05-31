package com.cts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Represents an error response sent when something goes wrong
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponce {
	private String message;
}
