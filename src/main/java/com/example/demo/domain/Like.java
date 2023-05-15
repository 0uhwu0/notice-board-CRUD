package com.example.demo.domain;

import org.springframework.stereotype.*;

import lombok.*;

@Data
public class Like {

	private Integer boardId;
	private String memberId;
}
