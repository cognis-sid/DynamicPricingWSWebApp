/* Copyright (c) 2020 RCL| All Rights Reserved*/
package com.rclgroup.dolphin.web.ws;

/* -------------------------- Change Log ----------------------------------------
##   DD/MM/YY       -User-              -TaskRef-            -ShortDescription-
1    28/03/20       Sushil               Initial Version
---------------------------------------------------------------------------------
*/
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 *This class is mainly intended for Base class of all service classes.
 * @author Cognis Solution
 */
public class BlcBaseService {
	
	private static final Logger logger = Logger.getLogger(BlcBaseService.class);

	protected final ObjectMapper getJSONMapper() {

		ObjectMapper mapper = new ObjectMapper();

		// Don't include any NULL fields.
		mapper.setSerializationInclusion(Include.NON_NULL);

		// Set the date serializer to match ISO 8601
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		mapper.setDateFormat(dateFormat);
		mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		return mapper;
	}

	protected final String getExceptionDetailAsJson(Throwable th) throws Exception {
		String exceptionJson = "";

		try {
			ObjectMapper mapper = getJSONMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			
			exceptionJson = mapper.writeValueAsString(th);
		} catch (Exception ex) {
			logger.log(Level.ERROR, ex.getMessage(), th);
		}
		return exceptionJson;
	}

	public final String serializeToJSONString(Object baseModel)  {

		ObjectMapper mapper = getJSONMapper();
		
		try {
			return mapper.writeValueAsString(baseModel);
		} catch (Exception ex) {
			// May be error due to fields requested during serialization (bad request?)
			if (JsonMappingException.class == ex.getClass() && ex.getCause() != null
					&& ex.getCause().getClass() == IllegalArgumentException.class) {
				throw new IllegalArgumentException(ex.getCause().getMessage(), ex.getCause());
			}
			throw new IllegalArgumentException(ex.getMessage(), ex);

		}

	}
}
