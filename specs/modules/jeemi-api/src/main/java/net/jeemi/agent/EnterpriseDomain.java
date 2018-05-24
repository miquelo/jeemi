package net.jeemi.agent;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Enterprise domain.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface EnterpriseDomain
{
	EnterpriseDomainName getName();
	
	Stream<Application> applications();
	
	Optional<Application> find(ApplicationName name);
	
	Application run(ApplicationParams params);
}
