/**
 * @authour Jacky
 * @data Dec 31, 2019
 */
package com.example.project.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.project.common.Dict;
import com.example.project.entity.User;
import com.example.project.query.Search;

/**
 * @author Jacky
 *
 */
public class BaseService<T> {
	protected Pageable buildPageable(Search search) {
		return buildPageable(search, new Sort(Sort.Direction.DESC, new String[] {Dict.ID}));
	}
	
	protected Pageable buildPageable(Search search, Sort sort) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(search.getPageCount() - 1, search.getPageSize(), sort);
		return pageable;
	}
	
	/**
	 * @param root
	 * @param query
	 * @param criteriaBuilder
	 * @param list
	 * @param search
	 */
	protected void buildPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder,
			List<Predicate> list, Search search) {
		// TODO Auto-generated method stub
		if (null != search.getId()) {
			list.add(criteriaBuilder.equal(root.get(Dict.ID).as(Long.class), search.getId()));
		}
		
	}
}
