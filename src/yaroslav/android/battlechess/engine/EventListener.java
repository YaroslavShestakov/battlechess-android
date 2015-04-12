package yaroslav.android.battlechess.engine;

/**
 * Interface that can be implemented by event listeners
 * @author Yaroslav Shestakov
 *
 */
public interface EventListener {
	/**
	 * Executes code when event is received
	 * @param event An event from the game
	 */
	public void onEvent(Event event);
}
