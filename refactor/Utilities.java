/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.agentbond.refactor;

import java.util.ArrayList;

/**
 *
 * @author Snipercat
 */
public class Utilities {

    static int MAX_COST = 999;
    
    static boolean isSameNode(Node NodeA, Node NodeB) {
        return NodeA.getId() == NodeB.getId();
    }
    
    static String getIdFromPosition(int[] position){
        return position[0]+","+position[1];
    }

    static ArrayList<int[]> applyDFS(Graph grafo, Node actualNode) {
        //TODO realizar busqueda de nodos vacios en el grafo partiendo del nodo actual
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
}
