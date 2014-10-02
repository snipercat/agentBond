/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.agentbond;

import java.util.ArrayList;
import unalcol.agents.examples.labyrinth.teseo.simple.*;
import unalcol.agents.simulate.util.SimpleLanguage;

/**
 *
 * @author Jonatan
 */
public class JamesBond extends SimpleTeseoAgentProgram {
    
    private int orientation=0; // 0=N 1=E 2=S 3=W
    private int[] position=new int[]{0,0}; //Position(x,y)
    private int[] lastVisitedNode = new int[]{0,0};// Position of last node (x,y)
    private ArrayList<int[]> targetPath = new ArrayList<>(); //Path of nodes that the agent need to visit.
    private Graph grafo = new Graph();
    private int   lastDirectionTaken = 0;
    
    public JamesBond(SimpleLanguage _language) {
     super.setLanguage( _language);
    }
    
    public JamesBond() {
     //grafo.add(new Nodo(position));
    } 
    
    
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        
        //SI YA LLEGO A LA META, NO HACER NADA
        if (MT) return -1;
      
    // ACCIONES POR REFLEJO DONDE NO HAY INTERSECCIONES
        // DEVOLVERSE
        if(PF&&PD&&PI){
            updateOrientation(2);
            updatePosition();
            return 2;
        }
        //GIRAR IZQUIERDA
        if(PD&&PF){
            updateOrientation(3);
            updatePosition();
            return 3;
        }
        //GIRAR DERECHA 
        if(PI&&PF){
            updateOrientation(1);
            updatePosition();
            return 1;
        }
        //SEGUIR AVANZANDO
        if(PI&&PD&&!PF){
            updatePosition();
            return 0;
        }
        
     // ACCIONES CUANDO HAY INTERSECCION
        //Si no hay nodos en el grafo, insertar el primero
        if( grafo.isEmpty()){
            Nodo firstNode = new Nodo(position);
            firstNode.addAllChilds(PF, PD, PA ,PI , orientation);
            grafo.add(firstNode);
            int action = firstNode.getRandomChildAction(orientation);
            this.lastVisitedNode = this.position.clone();
            updateOrientation(action);
            lastDirectionTaken = orientation;
            updatePosition();
            return action;
        }
        
       if(targetPath.isEmpty()){
        //SI NO HAY UN OBJETIVO DEFINIDO ES POR QUE SIGUE EXPLORANDO.
           //Buscar si ya está guardado el nodo en el grafo
           Nodo actualNode  = this.grafo.searchbyPosition(position);
           Nodo lastNode    =   this.grafo.searchbyPosition(lastVisitedNode);
           //Si está guardado
           if( actualNode != null){
               //Enlazar este nodo con el nodo anterior, si es el mismo, eliminar el hijo
               if(actualNode.samePosition(lastNode.getPosicion())){
                   lastNode.deleteChildAt(lastDirectionTaken);
               }
               else{
                    actualNode.setHijo(lastNode, orientation, 2);
                    lastNode.addChild( actualNode, lastDirectionTaken );
               }
           }
           else{ //Si no está guardado
               //Guardar ,enlaza y define los lugares no explorados.
               actualNode = new Nodo(position);
               actualNode.setHijo(lastNode, orientation, 2);
               actualNode.addEmptyChilds(PF, PD,PI , orientation);
               lastNode.addChild( actualNode, lastDirectionTaken );
               grafo.add(actualNode);
           }
               
        //Actualizar ultimo nodo visitado
        this.lastVisitedNode = this.position.clone();

        //Buscar vacio más próximo
        //Si el nodo tiene vasios, seleccionar uno al azar.
        int[] pos = actualNode.getRandomEmptyChildPosition(orientation);
        if(pos != null){
            targetPath.add( pos ); //Actuaizar target con las acciones que se deben ejecutar.

        }
        else{
        // TODO Algoritmo de Busqueda que retorne el camino hacia el nodo sin explorar más cercano.
            targetPath = grafo.applyDFS(actualNode);
            
        }
       }
       
       //SI HAY UN OBJETIVO DEFINIDO VA A EL
       if(!targetPath.isEmpty()){// Si hay un objetivo 
          int[] targetPos = targetPath.get(0); //Obtener primer camino
          Nodo nodoActual = grafo.searchbyPosition(position);
          int accion = nodoActual.getAction( targetPos, orientation ); // retorna la acción que se debe tomar de acuerdo a la posición deseada y a la orientación.
          
          //Actualizar datos
          this.lastVisitedNode = this.position.clone(); //Actualizar ultimo nodo visitado
          updateOrientation(accion); //actualiza orientación
          updatePosition(); //actualiza posición
          
          targetPath.remove(0); //Elimina path
          lastDirectionTaken = orientation;
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
