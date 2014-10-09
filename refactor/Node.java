/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.agentbond.refactor;

import unalcol.agents.examples.labyrinth.teseo.agentbond.Nodo;

/**
 *
 * @author SNIPERCAT
 */
public class Node {
    
    private String id;
    private int[]  position;
    
    // Identificador de los hijos y el costo
    //0 Up | 1 Rigth | 2 Down | 3 Left
    private String[] childs = new String[4];;
    private int[]   costToChilds = new int[4];;
    
    boolean visited = true; //Ya se ha pasado por ese nodo?

    Node(int[] position) {
        this.id = Utilities.getIdFromPosition(position);
        this.position = position.clone();
    }
    
    Node(int[] position, boolean visited) {
        this.id = Utilities.getIdFromPosition(position);
        this.position = position.clone();
        this.visited = visited;
    }
    
//**** GETT AND SETTER 
    

    public String getId() {
        return id;
    }

    public int[] getPosition() {
        return position;
    }

    public String[] getChilds() {
        return childs;
    }

    /**
     * asigna un hijo en el lugar indicador
     * @param child
     * @param place  0 Up | 1 Rigth | 2 Down | 3 Left
     */
    public void setChildAt(String child, int place) {
        this.childs[place] = child;
    }

    public int[] getCostToChilds() {
        return costToChilds;
    }

    public void setCostToChilds(int[] costToChilds) {
        this.costToChilds = costToChilds;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
//****
    

    /**
     * Obtiene un hijo al aar y llama getActionToChild para obtener la acción que se debe realizar
     * @param orientation
     * @return 
     */
    int getRandomChildAction(int orientation) {
        //TODO Obtiene un hijo al aar y llama getActionToChild para obtener la acción que se debe realizar
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * elimina un hijo en la posición indicada
     * @param lastDirectionTaken 
     */
    void deleteChildAt(int place) {
        childs[place] = null;
        costToChilds[place] = Utilities.MAX_COST;
    }

    /**
     * agrega un nodo en la dirección opuesta del agente, usado para asignar el "padre" a un nodo nuevo
     * @param parent
     * @param agentOrientation
     */
    void setParent(Node parent, int agentOrientation) {
        //determina en que posición se debe asignar el hijo dependiendo de la posición local
        // se le suma 2 para determinar la posición opuesta
        int globalPosition = (agentOrientation + 2) %4;        
        this.childs[globalPosition] = parent.getId();

    }

    /**
     * Agrega el hijo en el lugar indicado
     * @param childNode
     * @param place //0 Up | 1 Rigth | 2 Down | 3 Left
     */
    void setChild(Node childNode, int place) {
        this.childs[place] = childNode.getId();
        
    }
    
    /**
     * Agrega un hijo vacio no visitado en el lado indicado, la posición dependerá
     * de la posición del nodo y del lugar indicado.
     * @param place Lugar en donde se ubicará el hijo nuevo
     * @return hijo creado para que sea agregado al grafo.
     */
    public Node setEmpyChildAt( int place){
        
        int[] childPosition = null;
        
        switch(place){
        case 0:
            childPosition =  new int[]{this.position[0]-1 , this.position[1]};
            break;
        case 1:
            childPosition =  new int[]{this.position[0] , this.position[1]+1};
            break;
        case 2:
            childPosition =  new int[]{this.position[0]+1 , this.position[1]};

            break;
        case 3:
            childPosition =  new int[]{this.position[0] , this.position[1]-1};
            break;
        }
        
        Node child = new Node(childPosition, false);
        
        this.childs[place] = child.getId();
        this.costToChilds[place]= -1;
        
        
        return child;
    }

    /**
     * retorna la acción que se debe tomar para ir a uno de los hijos,
     * dependiendo de la orientación
     * 
     * @param childID ID del hijo al que se quiere mover
     * @param orientation
     * @return 
     */
    int getActionToChild(String childID, int orientation) {
        
         int rotations = 0;
        int targeOrientation=0;
/*         
        System.out.println("----//// CHILDS");
        for (int r = 0; r < childs.length; r++){
            System.out.println(childs[r]);
        }
        System.out.println("----//// END CHILDS");
  */      
        if(  childs[0] != null && childs[0].equals(childID)){
            targeOrientation = 0;
        }
        if(childs[1] != null && childs[1].equals(childID)){
            targeOrientation = 1;
        }
        if(childs[2] != null && childs[2].equals(childID)){
            targeOrientation = 2;
        }
        if(childs[3] != null && childs[3].equals(childID)){
            targeOrientation = 3;
        }
        
        while(targeOrientation != (orientation+rotations)%4 ){
            rotations++;
        }
        
        return rotations; 
    }
    
    
    String getChildsasString(){
        String ret="";
        System.out.println("----//// CHILDS");
        for (int r = 0; r < childs.length; r++){
            ret += childs[r]+ " | ";
        }
        return ret;
    }
}
