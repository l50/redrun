package redrun.model.constants;

/**
 * An enum of all possible game states. This helps control the logical flow of
 * the game.
 * 
 * @author J. Jake Nichol
 * @version 1.0
 * @since 11-24-14
 */
public enum GameState
{
  /** Game play is available. */
  PLAY,

  /** Player has won. */
  WINNER,

  /** Player has lost. */
  LOSER,

  /** Menu is up. */
  MAIN_MENU;
}
