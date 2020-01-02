/**
 * @authour Jacky
 * @data Dec 31, 2019
 */
package com.example.project.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jacky
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search {
	private Long id;
	private String keyword;
	private Integer pageCount = 1;
	private Integer pageSize = 20;
	private String startTime;
	private String endTime;
	
	public Search(String keyword, Integer pageSize, Integer pageCount, String startTime, String endTime) {
		this.keyword = keyword;
		this.pageSize = pageSize;
		this.pageCount = pageCount;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
