/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samsung_sp2.pkg3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Jose-PC
 */
public class Samsung_sp23 {
static Scanner sc = new Scanner(System.in);
    
    enum PIECE { EMPTY, WALL, ROOK }
    
    public static void main(String[] args) {
        
        System.out.print("Enter board size: ");
        int boardSize = sc.nextInt();
        sc.nextLine();
        
        //initialize board
        PIECE [][]gameBoard = new PIECE[boardSize][boardSize];
        char [][]input = new char[boardSize][boardSize];
        
        //fill up board with values
        System.out.println("Fill up the board");
        String lineInput;
        for (int x = 0; x < boardSize; x++) {
            lineInput = sc.nextLine().substring(0, boardSize);
            for (int y = 0; y < lineInput.length(); y++) {
                input[x][y] = lineInput.charAt(y);
                gameBoard[x][y] = getPieceEquivalent(lineInput.charAt(y));
            }
        }
        
        performCheck(gameBoard);
        
    }
    
    static void performCheck(PIECE [][]p){
        
        PIECE [][]board = p;
        int rooks = 0;
        for (int vertical = 0; vertical < board.length; vertical++) {
            for (int horizontal = 0; horizontal < board.length; horizontal++) {
                
                int verticalTarget = vertical;
                int horizontalTarget = horizontal;
                
                
                List <PIECE>v = new ArrayList<PIECE>();
                List <PIECE>h = new ArrayList<PIECE>();
                
                for (int i = 0; i < board.length; i++) {
                    h.add(board[vertical][i]);
                    v.add(board[i][horizontal]);
                }
                
                if (isPossible(board,v,h,verticalTarget,horizontalTarget)) {
                    board[vertical][horizontal] = PIECE.ROOK;
                    rooks++;
                }
                
                
            }
        }
        
        System.out.println("Number of rooks: " + rooks);
    }
    
    static boolean isPossible(  PIECE [][]board, 
                                List v, 
                                List h, 
                                int verticalTarget, 
                                int horizontalTarget){
        
        if (board[verticalTarget][horizontalTarget] == PIECE.EMPTY){
            
            Boolean isVertical = false, isHorizontal = false;
        
            if (verticalTarget == 0) {
                if (v.get(1) == PIECE.WALL) {
                    isVertical = true;
                }else{
                    if (v.contains(PIECE.ROOK)) {
                        if (v.contains(PIECE.WALL)) {
                            isVertical = v.indexOf(PIECE.ROOK) > v.indexOf(PIECE.WALL);
                        }else{
                            isVertical = false;
                        }
                    }else{
                        isVertical = true;
                    }
                }
            }
            
            if (horizontalTarget == 0) {
                if (h.get(1) == PIECE.WALL) {
                    isHorizontal = true;
                }else{
                    if (h.contains(PIECE.ROOK)) {
                        if (h.contains(PIECE.WALL)) {
                            isHorizontal = h.indexOf(PIECE.ROOK) > h.indexOf(PIECE.WALL);
                        }else{
                            isHorizontal = false;
                        }
                    }else{
                        isHorizontal = true;
                    }
                }
            }

            if (verticalTarget == v.size()-1) {
                if (v.get(verticalTarget-1) == PIECE.WALL) {
                    isVertical = true;
                }else{
                    if (v.contains(PIECE.ROOK)) {
                        if (v.contains(PIECE.WALL)) {
                            isVertical = v.indexOf(PIECE.WALL) > v.indexOf(PIECE.ROOK);
                        }else{
                            isVertical = false;
                        }
                    }else{
                        isVertical = true;
                    }
                }
            }
            
            if (horizontalTarget == h.size()-1) {
                if (h.get(horizontalTarget-1) == PIECE.WALL) {
                    isHorizontal = true;
                }else{
                    if (h.contains(PIECE.ROOK)) {
                        if (h.contains(PIECE.WALL)) {
                            isHorizontal = h.indexOf(PIECE.WALL) > h.indexOf(PIECE.ROOK);
                        }else{
                            isHorizontal = false;
                        }
                    }else{
                        isHorizontal = true;
                    }
                }
            }

            if (verticalTarget > 0 && verticalTarget < v.size()-1) {
                boolean subleft = false, subright = false;
                
                if (v.get(verticalTarget-1) == PIECE.WALL && v.get(verticalTarget+1) == PIECE.WALL) {
                    isVertical = true;
                }else if(!v.contains(PIECE.ROOK)){
                    isVertical = true;
                }else{
                    if (v.get(verticalTarget-1) == PIECE.WALL && v.subList(0, verticalTarget-1).size() == 1) {
                        subleft = true;
                    }else if(v.get(verticalTarget-1) == PIECE.ROOK){
                        subleft = false;
                    }else{
                        //check left
                        if (v.subList(0, verticalTarget-1).contains(PIECE.ROOK)) {
                            if (v.subList(0, verticalTarget-1).contains(PIECE.WALL)) {
                                subleft = v.subList(0, verticalTarget-1).indexOf(PIECE.WALL) 
                                          >
                                          v.subList(0, verticalTarget-1).indexOf(PIECE.ROOK);
                            }else{
                                subleft = false;
                            }
                        }else{
                            subleft = true;
                        }
                        
                        //check right
                        if (v.get(verticalTarget+1) == PIECE.WALL && v.subList(verticalTarget+1, v.size()-1).size() == 1) {
                            subright = true;
                        }else{
                            if (v.subList(verticalTarget+1, v.size()-1).contains(PIECE.ROOK)) {
                                if (v.subList(verticalTarget+1, v.size()-1).contains(PIECE.WALL)) {
                                    subright = v.subList(verticalTarget+1, v.size()-1).indexOf(PIECE.WALL) 
                                               <
                                               v.subList(verticalTarget+1, v.size()-1).indexOf(PIECE.ROOK);
                                }else{
                                    subright = false;
                                }
                            }else{
                                subright = true;
                            }
                        }
                    }
                    
                    isVertical = subleft && subright;
                }
            }
            
            if (horizontalTarget > 0 && horizontalTarget < h.size()-1) {
                boolean subleft = false, subright = false;
                
                if (h.get(horizontalTarget-1) == PIECE.WALL && h.get(horizontalTarget+1) == PIECE.WALL) {
                    isHorizontal = true;
                }else if(!h.contains(PIECE.ROOK)){
                    isHorizontal = true;
                }else{
                    if (h.get(horizontalTarget-1) == PIECE.WALL && h.subList(0, horizontalTarget-1).size() == 1) {
                        subleft = true;
                    }else if(h.get(horizontalTarget-1) == PIECE.ROOK){
                        subleft = false;
                    }else{
                        //check left
                        if (h.subList(0, horizontalTarget-1).contains(PIECE.ROOK)) {
                            if (h.subList(0, horizontalTarget-1).contains(PIECE.WALL)) {
                                subleft = h.subList(0, horizontalTarget-1).indexOf(PIECE.WALL) 
                                          >
                                          h.subList(0, horizontalTarget-1).indexOf(PIECE.ROOK);
                            }else{
                                subleft = false;
                            }
                        }else{
                            subleft = true;
                        }
                        
                        //check right
                        if (h.get(horizontalTarget+1) == PIECE.WALL && h.subList(horizontalTarget+1, h.size()-1).size() == 1) {
                            subright = true;
                        }else{
                            if (h.subList(horizontalTarget+1, h.size()-1).contains(PIECE.ROOK)) {
                                if (h.subList(horizontalTarget+1, h.size()-1).contains(PIECE.WALL)) {
                                    subright = h.subList(horizontalTarget+1, h.size()-1).indexOf(PIECE.WALL) 
                                               <
                                               h.subList(horizontalTarget+1, h.size()-1).indexOf(PIECE.ROOK);
                                }else{
                                    subright = false;
                                }
                            }else{
                                subright = true;
                            }
                        }
                    }
                    
                    isHorizontal = subleft && subright;
                }
            }
            
           return isVertical && isHorizontal;
        }
       
       return false;
    }
    
    
    static PIECE getPieceEquivalent(char c){
        switch(c){
            case 'X': case 'x': return PIECE.WALL;
            case '.': return PIECE.EMPTY;
            default: return null;
        }
    }
    
    static void printBoard(PIECE [][]p){
        System.out.print("\n");
        for (int x = 0; x < p.length; x++) {
            for (int y = 0; y < p.length; y++) {
                System.out.print(p[x][y] + "\t");    
            }
            System.out.print("\n");
        }
    }
    
    
}
