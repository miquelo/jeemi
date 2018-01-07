package net.jeemi.agent;

import java.util.stream.Stream;

/**
 * Agent.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface Agent
{
	Stream<Layout> layouts();
}
