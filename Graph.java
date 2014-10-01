/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.agentbond;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.xml.soap.Node;


/**
 *
 * @author Snipercat
 */
public class Graph {
    
    LinkedList<Nodo> Nodes;
    

    public Graph() {
         Nodes= new LinkedList<>();
    }
    
    public Nodo searchbyPosition(int[] pos){
    
        Nodo node = new Nodo();
        
        for(Nodo nodo:Nodes){
            if(nodo.samePosition(pos))
                    return nodo;
        }
        return null;
    }
    
    public void add( Nodo node){
        Nodes.add(node);
    }
    
    public boolean isEmpty(){
        return Nodes.isEmpty();
    }
    
    
    public ArrayList<int[]> applyDFS(Nodo root){
        SearchNode rootNode= new SearchNode(root);
        
        //Guarda las posiciones visitadas
        LinkedList<int[]> visitedPositions = new LinkedList<int[]>();
        visitedPositions.add(rootNode.getPosition());
        
        Queue q = new LinkedList();
        q.add(rootNode);
        
        while(!q.isEmpty()){
                SearchNode s = (SearchNode)q.poll();
                Nodo n = s.getNode();                
                ArrayList<Nodo> sucessors = n.getSucessors();
                
                for (Nodo hijo : sucessors) {

                    //Si no está en la lista
                    if( !inList(visitedPositions, hijo.getPosicion())){
                        //si el hijo no ha sido visitado, retornar camino.
                        if( !hijo.isVisitado()){
                            ArrayList<int[]> path = s.getTargetPath();
                            path.add( hijo.getPosicion() );
                            return path;
                        }
                        //si no ha sido visitado, se agrega en la cola y en la lista de posiciones visitadas
                        visitedPositions.add(hijo.getPosicion());
                        q.add( new SearchNode(hijo, s.getTargetPath(), hijo.getPosicion()) );
                    }
		}   
        }
        
        return null;
    }

    private boolean inList(LinkedList<int[]> visitedPositions, int[] posicion) {
        for (int[] c : visitedPositions){
            if(c[0]==posicion[0] && c[1] == posicion[1])
                return true;
        }
        return false;
    }

    
    
}
