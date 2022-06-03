/* Copyright (c) 2020 RCL| All Rights Reserved*/
package com.rclgroup.dolphin.web.ws;

/* -------------------------- Change Log ----------------------------------------
##   DD/MM/YY       -User-              -TaskRef-            -ShortDescription-
1    19/03/21        Sushil               Initial Version
---------------------------------------------------------------------------------
*/
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * This class is extends of {@code Application}
 * @author Cognis Solution
 */
public class BlcApplication extends Application {
	public Set<Class<?>> getClasses() {
		Set<Class<?>> setOfService = new HashSet<Class<?>>();
		setOfService.add(SearchUserService.class);
		setOfService.add(QuoteService.class);
		setOfService.add(MobileService.class);
		setOfService.add(DynamicPricingService.class);
		return setOfService;
	}
}