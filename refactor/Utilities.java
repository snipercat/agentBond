/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.agentbond.refactor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import unalcol.agents.examples.labyrinth.teseo.agentbond.Nodo;
import unalcol.agents.examples.labyrinth.teseo.agentbond.refactor.SearchNode;

/**
 *
 * @author Snipercat
 */
public class Utilities {

    static int MAX_COST = 999;
    static int LIMIT_AGENT_TICK = 3;
    
    static boolean isSameNode(Node NodeA, Node NodeB) {
        return NodeA.getId() == NodeB.getId();
    }
    
    static String getIdFromPosition(int[] position){
        return position[0]+","+position[1];
    }

    static ArrayList<int[]> applyDFS(Graph grafo, Node actualNode) {
        //TODO realizar busqueda de nodos vacios en el grafo partiendo del nodo actual
        
        SearchNode rootNode= new SearchNode(actualNode);
        
        //Guarda las posiciones visitadas
        LinkedList<int[]> visitedPositions = new LinkedList<>();
        visitedPositions.add(rootNode.getPosition());
        
        Queue q = new LinkedList();
        q.add(rootNode);
        
        while(!q.isEmpty()){
                SearchNode s = (SearchNode)q.poll();
                Node n = s.getNode();                
                ArrayList<Node> sucessors = grafo.getSucessors(n);
                
                for (Node hijo : sucessors) {

                    //Si no está en la lista
                    if( !inList(visitedPositions, hijo.getPosition())){
                        //si el hijo no ha sido visitado, retornar camino.
                        if( !hijo.isVisited()){
                            ArrayList<int[]> path = s.getTargetPath();
                            path.add( hijo.getPosition() );
                            return path;
                        }
                        //si no ha sido visitado, se agrega en la cola y en la lista de posiciones visitadas
                        visitedPositions.add(hijo.getPosition());
                        q.add( new SearchNode(hijo, s.getTargetPath(), hijo.getPosition()) );
                    }
		}   
        }
        
        return null;
    }
    
    private static boolean inList(LinkedList<int[]> visitedPositions, int[] posicion) {
        for (int[] c : visitedPositions){
            if(c[0]==posicion[0] && c[1] == posicion[1])
                return true;
        }
        return false;
    }
}
    
