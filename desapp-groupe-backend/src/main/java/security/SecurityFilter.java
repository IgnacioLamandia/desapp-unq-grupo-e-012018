package security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
public class SecurityFilter implements Filter {

	private static String CLIENT_ID;
	private GoogleIdTokenVerifier verifier;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
		HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
		String accessToken = httpRequest.getHeader("Authorization");

		if(accessToken != null){
			try {
				GoogleIdToken idToken = verifier.verify(accessToken);
				System.out.println("################################");
				System.out.println(idToken);
				System.out.println("################################");
				if (idToken != null) {
					  Payload payload = idToken.getPayload();

					  // Print user identifier
					  String userId = payload.getSubject();
					  System.out.println("User ID: " + userId);

					  // Get profile information from payload
					  String email = payload.getEmail();

					  // Use or store profile information
					  // ...
					  filterChain.doFilter(servletRequest, servletResponse);
					  
					} else {
					  System.out.println("Invalid ID token.");
					  httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					}
				
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
			
		} else {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
//		CLIENT_ID = "1095548920097-gqcq4996i28jqbdciqr6bpuue7uk4eu0.apps.googleusercontent.com";
		CLIENT_ID = "742198362537-437c5bp4k8c1kjdctnd5obucvu396drr.apps.googleusercontent.com";// secretclient=-q8yisExffkOdaZqRPXEgZDV
		this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
			    .setAudience(Collections.singletonList(CLIENT_ID))
			    .build();		
	}

}
