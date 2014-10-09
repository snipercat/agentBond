/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.agentbond.refactor;

import java.util.ArrayList;
import unalcol.agents.Action;
import unalcol.agents.Percept;
import unalcol.agents.examples.labyrinth.teseo.simple.*;
import unalcol.agents.simulate.util.SimpleLanguage;

/**
 *
 * @author Jonatan
 */
public class JamesBond2 extends SimpleTeseoAgentProgram {
    
                    
    
    private int orientation=0; // 0=N 1=E 2=S 3=W
    private int[] position=new int[]{0,0}; //Position(x,y)
    private int[] lastVisitedNode = new int[]{0,0};// Position of last node (x,y)
    private ArrayList<int[]> targetPath = new ArrayList<>(); //Path of nodes that the agent need to visit.
    private Graph grafo = new Graph();
    private int   lastDirectionTaken = 0;
    
    
    public JamesBond2(SimpleLanguage _language) {
     super.setLanguage( _language);
    }
    
    public JamesBond2() {
     //grafo.add(new Nodo(position));
    } 
    
    
    /**
   * execute
   *
   * @param perception Perception
   * @return Action[]
   */
    @Override
  public Action compute(Percept p){
     
      
      // Si hay un agente en el camino esperar o buscar otro camino
      try{
        boolean AF = ( (Boolean) p.getAttribute(language.getPercept(5))).
            booleanValue();
        boolean AD = ( (Boolean) p.getAttribute(language.getPercept(6))).
            booleanValue();
        boolean AA = ( (Boolean) p.getAttribute(language.getPercept(7))).
            booleanValue();
        boolean AI = ( (Boolean) p.getAttribute(language.getPercept(8))).
            booleanValue();

        if(AF || AD || AA || AI){
            //TODO accion cuando hay agente.
            return new Action(language.getAction(0));
        }
      }
      catch(Exception e){
      }
    
      // Si no hay agente, continúa normal.
    
    if( cmd.size() == 0){

      boolean PF = ( (Boolean) p.getAttribute(language.getPercept(0))).
          booleanValue();
      boolean PD = ( (Boolean) p.getAttribute(language.getPercept(1))).
          booleanValue();
      boolean PA = ( (Boolean) p.getAttribute(language.getPercept(2))).
          booleanValue();
      boolean PI = ( (Boolean) p.getAttribute(language.getPercept(3))).
          booleanValue();
      boolean MT = ( (Boolean) p.getAttribute(language.getPercept(4))).
          booleanValue();

      int d = accion(PF, PD, PA, PI, MT);
      
      if (0 <= d && d < 4) {
        for (int i = 1; i <= d; i++) {
          cmd.add(language.getAction(3)); //rotate
        }
        cmd.add(language.getAction(2)); // advance
      }
      else {
        cmd.add(language.getAction(0)); // die
      }
    }
    
    String x = cmd.get(0);
    cmd.remove(0);
    
    if( x.equals(language.getAction(3))){
        updateOrientation(1);
    }
    if( x.equals(language.getAction(2))){
            updatePosition();
    }
    return new Action(x);
  }
    
    
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        //SI YA LLEGO A LA META, NO HACER NADA
        if (MT) {
            return -1;
        }
        
        // ACCIONES POR REFLEJO DONDE NO HAY INTERSECCIONES
        // DEVOLVERSE
        if (PF && PD && PI) {
            //updateOrientation(2);
            //updatePosition();
            return 2;
        }
        //GIRAR IZQUIERDA
        if (PD && PF) {
            //updateOrientation(3);
            //updatePosition();
            return 3;
        }
        //GIRAR DERECHA
        if (PI && PF) {
            //updateOrientation(1);
            //updatePosition();
            return 1;
        }
        //SEGUIR AVANZANDO
        if (PI && PD && !PF) {
            //updatePosition();
            return 0;
        }
        
        
        // ACCIONES CUANDO HAY INTERSECCION
        //Si no hay nodos en el grafo, insertar el primero
        if (grafo.isEmpty()) {
            Node firstNode = new Node(position);
            grafo.addAllChilds(firstNode, PF, PD, PA, PI, orientation);
            grafo.addNode(firstNode);
            int[] pos =  grafo.getRandomChildPosition(firstNode);
            //int action = firstNode.getRandomChildAction(orientation);
            this.lastVisitedNode = this.position.clone();
            //updateOrientation(action);
            //updatePosition();
            //lastDirectionTaken = (action + orientation) % 4;
            targetPath.add(pos);
            //return action;
        }
        
        
        if (targetPath.isEmpty()) {
            //SI NO HAY UN OBJETIVO DEFINIDO ES POR QUE SIGUE EXPLORANDO.
            //Buscar si ya está guardado el nodo en el grafo
            Node actualNode = this.grafo.getNodeByPosition(position);
            Node lastNode = this.grafo.getNodeByPosition(lastVisitedNode);
            //Si está guardado
            if (actualNode != null && actualNode.isVisited()) {   
                //Enlazar este nodo con el nodo anterior, si es el mismo, eliminar el hijo
                if ( Utilities.isSameNode( actualNode, lastNode)) {
                    lastNode.deleteChildAt(lastDirectionTaken);
                } else {
                    actualNode.setParent(lastNode, orientation);
                    lastNode.setChild(actualNode, lastDirectionTaken);
                }
            } else { //Si no está guardado
                //Guardar ,enlaza y define los lugares no explorados.
                actualNode = new Node(position);
                actualNode.setParent(lastNode, orientation);
                grafo.addNode(actualNode);
                grafo.addEmptyChilds(actualNode, PF, PD, PI, orientation);
                lastNode.setChild(actualNode, lastDirectionTaken);
            }
            //Actualizar ultimo nodo visitado
            this.lastVisitedNode = this.position.clone();
            //Buscar vacio más próximo
            //Si el nodo tiene vacios, seleccionar uno al azar.
            int[] pos = grafo.getRandomEmptyChildPosition(actualNode);
            if (pos != null) {
                targetPath.add(pos); //Actuaizar target con las acciones que se deben ejecutar.
            } else {
                // TODO Algoritmo de Busqueda que retorne el camino hacia el nodo sin explorar más cercano.
                System.out.println("SEARCH!!!!!"); 
                targetPath.clear();
                targetPath.add(  grafo.getRandomChildPosition(actualNode) );
                System.out.println("ADDED: "+ Utilities.getIdFromPosition(targetPath.get(0))); 
                //targetPath = Utilities.applyDFS(grafo, actualNode);
            }
        }
        
        
        //SI HAY UN OBJETIVO DEFINIDO VA A EL
        if (!targetPath.isEmpty()) {// Si hay un objetivo
            int[] targetPos = targetPath.get(0); //Obtener primer camino
            Node nodoActual = grafo.getNodeByPosition(position);
            
            // retorna la acción que se debe tomar de acuerdo a la posición deseada y a la orientación.
            int accion = nodoActual.getActionToChild( Utilities.getIdFromPosition(targetPos), orientation); 
System.out.println("pos: "+Utilities.getIdFromPosition(position)  +" targt: "+Utilities.getIdFromPosition(targetPos)+" Action "+accion);            
            //Actualizar datos
            this.lastVisitedNode = this.position.clone(); //Actualizar ultimo nodo visitado
            //updateOrientation(accion); //actualiza orientación
            //updatePosition(); //actualiza posición
            targetPath.remove(0); //Elimina path
            lastDirectionTaken = (accion + orientation) % 4;
//System.out.println("LAST: "+lastVisitedNode[0]+","+lastVisitedNode[1]);
            return accion;
        }
        return 0;
    }
    
    
    
     /**
     * Actualiza el parámetro de Orientación para conocer en que dirección está 
     * el agente despues de ejecutar las acciones.
     * @param r 
     */
    private void updateOrientation(int r){
        this.orientation = (this.orientation + r)%4;
    }
    
    /**
     * Actualiza el parámetro de posición para conocer en que posición está 
     * el agente despues de ejecutar las acciones.
     * @param r 
     */
    private void updatePosition(){
        if(this.orientation == 0)
            this.position[0] -=1;
        if(this.orientation == 2)
            this.position[0] +=1;
        
        if(this.orientation == 1)
            this.position[1] +=1;
        if(this.orientation == 3)
            this.position[1] -=1;
    }
    


   
    
}
