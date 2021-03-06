package com.heroku.eclipse.core.services.junit;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.heroku.eclipse.core.services.HerokuSession;
import com.heroku.eclipse.core.services.exceptions.HerokuServiceException;
import com.heroku.eclipse.core.services.junit.common.Credentials;


public class HerokuSessionSimpleTest extends HerokuSessionTest {

	public void testGetAPIKey() {
		HerokuSession session = getSession();
		String apiKey = session.getAPIKey();
		assertEquals("The returned api key must be the same as the provided api key.", Credentials.VALID_JUNIT_APIKEY1, apiKey);
	}
	
	public void testIsValid() {
		HerokuSession session = getSession();
		assertEquals("The session is expected to be valid", true, session.isValid());
	}
	
	public void testIsNotValid() throws HerokuServiceException {
		try {
			getService().setAPIKey(new NullProgressMonitor(), "bla");
		}
		catch ( HerokuServiceException e ) {
			assertEquals("Setting an invalid API key must fail", HerokuServiceException.INVALID_API_KEY, e.getErrorCode() );
		}
	}
	
}
