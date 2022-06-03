/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.rclgroup.dolphin.web.model.PropertyMod;
import com.rclgroup.dolphin.web.model.RequestQuote;

/**
 * Interface contain methods related to Search user.
 * 
 * @author Cognis Solution
 *
 */

public interface QuoteRequestDao {

	public String PCR_REQUEST_QUOTE_REQUEST = "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_REQUEST";
	public String PCR_REQUEST_QUOTE_REQUEST_SEARCH = "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_REQUEST_SEARCH";
	public String PCR_REQUEST_QUOTE_REQUEST_ACTION = "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_REQUEST_ACTION";
	public String PCR_REQUEST_QUOTE_DELETE = "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_DELETE";
	public String PCR_REQUEST_QUOTE_RATE_UPDATE = "VASAPPS.PCR_REQUEST_QUOTE.PCR_REQUEST_QUOTE_TARGET_RATE";

	public String create(RequestQuote requestQuote) throws DataAccessException;

	public List<RequestQuote> getSearchData(RequestQuote searchParam) throws DataAccessException;
	
	public void arrove(RequestQuote requestQuote)throws DataAccessException;
	public void reject(RequestQuote requestQuote)throws DataAccessException;
	
	public void delete(RequestQuote requestQuote)throws DataAccessException;
	
	public List<PropertyMod> validateShipper(RequestQuote quote) throws DataAccessException;
	
	public void updateTragetEtd(RequestQuote quote) throws DataAccessException;

}
