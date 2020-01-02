/**
 * @authour Jacky
 * @data Dec 31, 2019
 */
package com.example.project.controller;

import com.example.project.dto.Request;
import com.example.project.query.Search;

/**
 * @author Jacky
 *
 */
public class baseController {
	public Search toSearch(Request vo) {
		Search search = new Search(vo.getKeyword(), vo.getPageSize(), vo.getPageCount(), vo.getStartTime(), vo.getEndTime());
		return search;
	}
}
