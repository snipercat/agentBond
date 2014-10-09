/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.agentbond.refactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import unalcol.agents.examples.labyrinth.teseo.agentbond.Nodo;

/**
 *
 * @author SNIPERCAT
 */
public class Graph {
    HashMap<String,Node> graph;

    public Graph() {
        graph = new HashMap<>();
    }
    
    public void addNode(Node node){
        graph.put(node.getId(), node);
    }
    
    public Node getNodeByID( String id){
        return graph.get(id);
    }
    
    /**
     * Retorna un nodo dada su posición
     * @param position
     * @return 
     */
    public Node getNodeByPosition(int [] position){
        return graph.get( Utilities.getIdFromPosition(position));
    }
    
    /**
     * Obtener los hijos de un nodo
     * @param actualNode Nodo al cual se le retornarán los hijos
     * @param parentNode Nodo del cual se llego al nodo actual, no debe ser retornado.
     * @return Todos los hijos no nulos del nodo actual excepto el nodo padre.
     */
    public Node[] getChilds( Node actualNode, Node parentNode ){
        String[] childsID = actualNode.getChilds();
        //TODO Obtener los hijos de un nodo
        return null;
    }

    
    boolean isEmpty() {
        return graph.isEmpty();
    }

    /**
     * Agrega los hijos en donde no haya pared y los vincula con el nodo, no se tiene en cuenta la pared de atras
     * por que se supone que se llego por ese lado y ya se conoce el padre.
     * @param node
     * @param PF
     * @param PD
     * @param PI
     * @param orientation 
     */
    void addEmptyChilds(Node node, boolean PF, boolean PD, boolean PI, int orientation) {
        int globalOrientation = 0;
        
        //Agrega un hijo en donde no haya pared
        if(!PF){
            globalOrientation = orientation;
            addNode(node.setEmpyChildAt(globalOrientation));
        }
        if(!PD){
            globalOrientation = (orientation + 1)%4;
            addNode(node.setEmpyChildAt(globalOrientation));
        }
        if(!PI){
            globalOrientation = (orientation + 3)%4;
            addNode(node.setEmpyChildAt(globalOrientation));
        }
        /*System.out.println("Position "+node.getId());
        System.out.println("CHILDS ADDED!!!!! "+node.getChildsasString());
        System.out.println("END CHILDS ADDED!!!!!");
        */
      }
    
        
    

    
    /**
     * Obtiene la posición de uno de los hijos vacios del nodo
     * @param actualNode
     * @param orientation
     * @return 
     */
    int[] getRandomEmptyChildPosition(Node node) {
        String[] childsID = node.getChilds();
        ArrayList<Node> nodes = new ArrayList<>();        
        Node child;
        for(int c=0; c<childsID.length; c++){
            child = getNodeByID(childsID[c]);
            if( child != null && !child.isVisited())
                nodes.add(child);
        }
        
        if(nodes.isEmpty())
            return null;
        
        Random r = new Random();
        
        return nodes.get(r.nextInt(nodes.size())).getPosition();
    }

    void addAllChilds(Node firstNode, boolean PF, boolean PD, boolean PA, boolean PI, int orientation) {
        //TODO Agregar todos los hijos en donde no haya pared, se agregan como hijos nuevos, similar a addEmptyChilds, pero se tiene en cuenta la pared de atras
        
        int globalOrientation = 0;
        
        //Agrega un hijo en donde no haya pared
        if(!PF){
            globalOrientation = orientation;
            addNode(firstNode.setEmpyChildAt(globalOrientation));
        }
        if(!PD){
            globalOrientation = (orientation + 1)%4;
            addNode(firstNode.setEmpyChildAt(globalOrientation));
        }
        if(!PA){
            globalOrientation = (orientation + 2)%4;
            addNode(firstNode.setEmpyChildAt(globalOrientation));
        }
        if(!PI){
            globalOrientation = (orientation + 3)%4;
            addNode(firstNode.setEmpyChildAt(globalOrientation));
        }

        
    }
    
    int[] getRandomChildPosition(Node node) {
        String[] childsID = node.getChilds();
        ArrayList<Node> nodes = new ArrayList<>();        
        Node child;
        //**
        
   //     String info = "pos: "+node.getId()+ "Childs ["+ node.getChildsasString()+ "] ";
        
        //**
        
        
        for(int c=0; c<childsID.length; c++){
            child = getNodeByID(childsID[c]);
            if( child != null )
                nodes.add(child);
        }
        
        Random r = new Random();
        int n = r.nextInt(nodes.size());
        
//        info += "SelID: "+nodes.get(n).getId();
//        info += "Selpos: "+ Utilities.getIdFromPosition(nodes.get(n).getPosition());
//System.out.println(info);
        return nodes.get(n).getPosition();
    }
    
    
    public ArrayList<Node> getSucessors( Node node) {
        
        String[] childsID = node.getChilds();
        ArrayList<Node> sucesors = new ArrayList<>();
        Node child;

        for(int c=0; c<childsID.length; c++){
            child = getNodeByID(childsID[c]);
            if( child != null )
                sucesors.add(child);
        }
        
        
        
        
        return sucesors;
        
    }
    
    
}

