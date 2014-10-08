/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.agentbond.refactor;

/**
 *
 * @author SNIPERCAT
 */
public class Graph {
    //hashmap
    
    
    public void addNode(Node node){
        //TODO Agregar nodo al hashmap
    }
    
    public Node getNodeByID(){
    //TODO Obtener nodo por identificador
        
    return null;
    }
    
    public Node getNodeByPosition(int [] position){
        //TODO Obtener nodo por posicion
    return null;
    }
    
    /**
     * Obtener los hijos de un nodo
     * @param actualNode Nodo al cual se le retornarán los hijos
     * @param parentNode Nodo del cual se llego al nodo actual, no debe ser retornado.
     * @return Todos los hijos no nulos del nodo actual excepto el nodo padre.
     */
    public Node[] getChilds( Node actualNode, Node parentNode ){
        //TODO Obtener los hijos de un nodo
        return null;
    }

    
    boolean isEmpty() {
        //TODO El grafo está vacio?
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
      }

    
    /**
     * Obtiene la posición de uno de los hijos vacios del nodo
     * @param actualNode
     * @param orientation
     * @return 
     */
    int[] getRandomEmptyChildPosition(Node node) {
        //TODO Obtiene la posición de uno de los hijos vacios del nodo
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void addAllChilds(Node firstNode, boolean PF, boolean PD, boolean PA, boolean PI, int orientation) {
        //TODO Agregar todos los hijos en donde no haya pared, se agregan como hijos nuevos, similar a addEmptyChilds, pero se tiene en cuenta la pared de atras
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
