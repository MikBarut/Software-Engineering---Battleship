package controller

import util.Observable
import model.{GameBoard,Ship, Position, PlaceShipStategyTemplate}

class Controller(var gameBoard1: GameBoard, var gameBoard2: GameBoard) extends Observable{
    
    private var placeShipStrategy: PlaceShipStategyTemplate = null

    def setPlaceShipStrategy(strategy: PlaceShipStategyTemplate): Unit = {
        placeShipStrategy = strategy
    }

    def startGame(player: Int): Unit = {

        if(player == 2) return

        gameBoard1.generateField()
        gameBoard2.generateField()
        notifyObservers

    }

    def startGameWithStrategy(player: Int): Unit = {
        if (placeShipStrategy != null) {
            if(player == 1){
               gameBoard1 = placeShipStrategy.createNewGameField(gameBoard1.getSize()) 
            }
            if (player == 2){
                gameBoard2 = placeShipStrategy.createNewGameField(gameBoard2.getSize())
            }
            
            notifyObservers
        } else {
            startGame(player)
        }
    }

    def isGameOver(): Boolean = gameBoard1.isGameOver() || gameBoard2.isGameOver()

    def getShipsToPlace(player: Int): List[Ship] = {
        if (player == 1) gameBoard1.getShipsToPlace()
        else gameBoard2.getShipsToPlace()
    }

    def isCellContent(player: Int, row: Int, col: Int, or: Char): Boolean = {

        if (player == 1) gameBoard1.isCellContent(row,col,or)
        else gameBoard2.isCellContent(row,col,or)

    }

    def getGameBoardSize(): Int = {

        return gameBoard1.getSize()

    }

    def placeShip(player: Int, ship: Ship, position: (Int, Int), orientation: Char): Boolean = {

        var state = false

        if (player == 1) state = gameBoard1.placeShip(ship,position,orientation)
        else state = gameBoard2.placeShip(ship,position,orientation)
        notifyObservers
        return state
        


    }

    def makeMove(player: Int, position: Position): Boolean = {

        val x = position.getX()
        val y = position.getY()

        val hit = if (player == 1) gameBoard2.attack((x,y)) else gameBoard1.attack((x,y))
        notifyObservers
        hit

    }

    def getPlayerBoard(player: Int, hidden: Boolean = true): String = {
    
        if (player == 1) gameBoard1.printField(hidden) else gameBoard2.printField(hidden)
    
    }

    def getOpponentBoard(player: Int): String = {
    
        if (player == 1) gameBoard2.printField(hidden = true) else gameBoard1.printField(hidden = true)
    
    }

}