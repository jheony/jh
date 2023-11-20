package o1.snake

import o1.*

// The height and width of the game area, measured in squares that can hold
// a single segment of a snake.
val SizeInSquares = 32

// Constants that define the GUI size and speed:
val PixelsPerGridSquare = 20
val WorldSizeInPixels = PixelsPerGridSquare * SizeInSquares
val GameSpeed = 10

// Images used by the GUI:
val Background = rectangle(WorldSizeInPixels, WorldSizeInPixels, White)
val SegmentPic = circle(PixelsPerGridSquare * 1.5, Purple) // looks nicer if the segments are bigger than the actual squares
val FoodPic    = rectangle(PixelsPerGridSquare * 2 / 3, PixelsPerGridSquare * 2 / 3, Green)

// Helper function: given a GridPos, returns the Pos that matches its center in the image
def toPixelPos(gridPos: GridPos) = Pos(gridPos.x * PixelsPerGridSquare, gridPos.y * PixelsPerGridSquare)

// Set up the game:
val initialGridPosOfSnake = GridPos(SizeInSquares / 5, SizeInSquares / 2)
val game = SnakeGame(initialGridPosOfSnake, East)

// A view that will display the game:
object snakeView extends View(game, GameSpeed, "Snake"):

  // An image of the game world. From back to front: a solid white background, a snake, and a piece of food.
  def makePic =
    // TODO: should draw the entire snake, not just the head
    val snakePos = game.snakeSegments.map(toPixelPos)
    val foodPos  = toPixelPos(game.nextFood)
    Background.placeCopies(SegmentPic, snakePos).place(FoodPic, foodPos)

  override def onTick() =
    game.advance()

  override def isDone = game.isOver

  // Turns the snake whenever an arrow key or one of the WASD keys is pressed.
  override def onKeyDown(key: Key) =
    CompassDir.fromKey(key) match
      case Some(newDirection) =>
        game.snakeHeading = newDirection
      case None =>
        // not an arrow or WASD key; no need to do anything
end snakeView


@main def runSnakeApp() =
  snakeView.start()

