package net.jeemi.agent;

import java.util.stream.Stream;

/**
 * Definition of a layout with infrastructure elements collaboration.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface Layout
{
	Stream<EnterpriseDomain> domains();
	
	/**
	 * Layout name.
	 */
	LayoutName getName();
}
