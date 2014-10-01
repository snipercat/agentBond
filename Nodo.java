/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.agentbond;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import javax.xml.soap.Node;

/**
 *
 * @author chava_000
 */
public class Nodo implements Comparable, Cloneable{
    private String identificador;
    private Queue sucesores;
    
    
    //Nodos enlazados
    private Nodo hijoUP;
    private Nodo hijoDOWN;
    private Nodo hijoLEFT;
    private Nodo hijoRIGHT;
    private int[] posicion;
    private Boolean visitado =true;
    
    private String padre;
    Nodo clone;
    
    public Nodo(){
    }
    
    public Nodo(int[] position){
        this.posicion = position.clone();
    }
    
    public Nodo(boolean visitado, int[] posicion){
        this.visitado = visitado;
        this.posicion = posicion;
    }
    
    Nodo(String identificador){
        this.identificador = identificador;
        this.sucesores = new PriorityQueue();
        this.visitado = Boolean.FALSE;
        this.padre = "";
    }
    public Nodo(String identificador,Queue sucesores,Boolean visitado,String padre){
        this.identificador = identificador;
        this.sucesores = sucesores;
        this.visitado = visitado;
        this.padre = padre;
    }
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Queue getSucesores() {
        return sucesores;
    }

    public void setSucesores(Queue sucesores) {
        this.sucesores = sucesores;
    }

    public Boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(Boolean visitado) {
        this.visitado = visitado;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public int[] getPosicion() {
        return posicion;
    }

    public void setPosicion(int[] posicion) {
        this.posicion = posicion;
    }
    
    
    public boolean samePosition(int[] pos){
        return this.posicion[0] == pos[0] && this.posicion[1] == pos[1];
    }

    @Override
    public int compareTo(Object t) {
        return this.identificador.compareTo(((Nodo)t).getIdentificador());
    }
    
    public String toString(){
        return this.identificador+sucesores.toString();
    }
    
    /**
     * Agregar un hijo deacuerdo a la posición del agente.
     * @param hijo: Nodo que se agregará.
     * @param agentOrientation: Orientación actual del agente.
     * @param localPosition: Posición de acuerdo al agente en la que se detecto el hijo
     */
    public void setHijo(Nodo hijo, int agentOrientation, int localPosition){
    
        int globalPosition = (agentOrientation + localPosition) %4;
        
        if(globalPosition == 0)
               this.hijoUP = hijo;
        if(globalPosition == 1)
               this.hijoRIGHT = hijo;
        if(globalPosition == 2)
               this.hijoDOWN = hijo;
        if(globalPosition == 3)
               this.hijoLEFT = hijo;
    }
    
    /**
     * retorna la acción que se debe tomar para ir a uno de los hijos,
     * dependiendo de la orientación
     * 
     * @param targetPosition 
     */
    public int getAction(int[] targetPosition, int orientation){
        int rotations = 0;
        int targeOrientation=0;
        
        if(hijoUP != null && hijoUP.samePosition(targetPosition)){
            targeOrientation = 0;
        }
        if(hijoRIGHT != null && hijoRIGHT.samePosition(targetPosition)){
            targeOrientation = 1;
        }
        if(hijoDOWN != null && hijoDOWN.samePosition(targetPosition)){
            targeOrientation = 2;
        }
        if(hijoLEFT != null && hijoLEFT.samePosition(targetPosition)){
            targeOrientation = 3;
        }
        
        while(targeOrientation != (orientation+rotations)%4 ){
            rotations++;
        }
        
        return rotations;  
    }    

    
    /**
     * Agrega los hijos vacios en donde no hay pared.
     * @param PF
     * @param PD
     * @param PI
     * @param orientation 
     */
    public void addEmptyChilds(boolean PF, boolean PD, boolean PI, int orientation) {
        int globalOrientation = 0;
        //Si no pared al frente.
        if(!PF){
            globalOrientation = orientation;
            switch (globalOrientation){
                case 0:
                    hijoUP = new Nodo(false, new int[]{posicion[0]-1 , posicion[1]});
                    break;
                case 1:
                    hijoRIGHT = new Nodo(false, new int[]{posicion[0] , posicion[1]+1});
                    break;
                case 2:
                    hijoDOWN  = new Nodo(false, new int[]{posicion[0]+1 , posicion[1]});
                    break;
                case 3:
                    hijoLEFT  = new Nodo(false, new int[]{posicion[0] , posicion[1]-1});
                    break;
            }
        }
           
           //Si no pared a la derecha.
        if(!PD){
            globalOrientation = (orientation + 1)%4;
            switch (globalOrientation){
                case 0:
                    hijoUP = new Nodo(false, new int[]{posicion[0]-1 , posicion[1]});
                    break;
                case 1:
                    hijoRIGHT = new Nodo(false, new int[]{posicion[0] , posicion[1]+1});
                    break;
                case 2:
                    hijoDOWN  = new Nodo(false, new int[]{posicion[0]+1 , posicion[1]});
                    break;
                case 3:
                    hijoLEFT = new Nodo(false, new int[]{posicion[0] , posicion[1]-1});
                    break;
            }
        }
            
            //Si no pared a la derecha.
        if(!PI){
            globalOrientation = (orientation + 3)%4;
            switch (globalOrientation){
                case 0:
                    hijoUP = new Nodo(false, new int[]{posicion[0]-1 , posicion[1]});
                    break;
                case 1:
                    hijoRIGHT = new Nodo(false, new int[]{posicion[0] , posicion[1]+1});
                    break;
                case 2:
                    hijoDOWN  = new Nodo(false, new int[]{posicion[0]+1 , posicion[1]});
                    break;
                case 3:
                    hijoLEFT  = new Nodo(false, new int[]{posicion[0] , posicion[1]-1});
                    break;
            }    
        }
     }

    /**
     * Agrega todos los posibles caminos de una intersección
     * @param PF
     * @param PD
     * @param PA
     * @param PI
     * @param orientation 
     */
    public void addAllChilds(boolean PF, boolean PD, boolean PA, boolean PI, int orientation) {
        int globalOrientation;
        //Si no pared al frente.
        if(!PF){
            globalOrientation = orientation;
            switch (globalOrientation){
                case 0:
                    hijoUP = new Nodo(false, new int[]{posicion[0]-1 , posicion[1]});
                    break;
                case 1:
                    hijoRIGHT = new Nodo(false, new int[]{posicion[0] , posicion[1]+1});
                    break;
                case 2:
                    hijoDOWN  = new Nodo(false, new int[]{posicion[0]+1 , posicion[1]});
                    break;
                case 3:
                    hijoLEFT  = new Nodo(false, new int[]{posicion[0] , posicion[1]-1});
                    break;
            }
        }
           
           //Si no pared a la derecha.
        if(!PD){
            globalOrientation = (orientation + 2)%4;
            switch (globalOrientation){
               case 0:
                    hijoUP = new Nodo(false, new int[]{posicion[0]-1 , posicion[1]});
                    break;
                case 1:
                    hijoRIGHT = new Nodo(false, new int[]{posicion[0] , posicion[1]+1});
                    break;
                case 2:
                    hijoDOWN  = new Nodo(false, new int[]{posicion[0]+1 , posicion[1]});
                    break;
                case 3:
                    hijoLEFT  = new Nodo(false, new int[]{posicion[0] , posicion[1]-1});
                    break;
            }
        }
            
        //Si no pared atras
        if(!PA){
            globalOrientation = (orientation + 2)%4;
            switch (globalOrientation){
                case 0:
                    hijoUP = new Nodo(false, new int[]{posicion[0]-1 , posicion[1]});
                    break;
                case 1:
                    hijoRIGHT = new Nodo(false, new int[]{posicion[0] , posicion[1]+1});
                    break;
                case 2:
                    hijoDOWN  = new Nodo(false, new int[]{posicion[0]+1 , posicion[1]});
                    break;
                case 3:
                    hijoLEFT  = new Nodo(false, new int[]{posicion[0] , posicion[1]-1});
                    break;
            } 
        }
        
        //Si no pared a la derecha.
        if(!PI){
            globalOrientation = (orientation + 3)%4;
            switch (globalOrientation){
                case 0:
                    hijoUP = new Nodo(false, new int[]{posicion[0]-1 , posicion[1]});
                    break;
                case 1:
                    hijoRIGHT = new Nodo(false, new int[]{posicion[0] , posicion[1]+1});
                    break;
                case 2:
                    hijoDOWN  = new Nodo(false, new int[]{posicion[0]+1 , posicion[1]});
                    break;
                case 3:
                    hijoLEFT  = new Nodo(false, new int[]{posicion[0] , posicion[1]-1});
                    break;
            }    
        }
    }

    
    public int getRandomChildAction(int agentOrientation) {
        
        ArrayList<Nodo> childs = new ArrayList<>();
        
        if(hijoUP != null)
            childs.add(hijoUP);
        if(hijoDOWN != null)
            childs.add(hijoDOWN);
        if(hijoLEFT != null)
            childs.add(hijoLEFT);
        if(hijoRIGHT != null)
            childs.add(hijoRIGHT);
        
        int myRand = (int)(Math.random() * (childs.size() - 0) + 0);
        
        return getAction(childs.get(myRand).getPosicion(), agentOrientation);
    }

    /**
     * Agrega un hijo en la posición indicada
     * @param newNode
     * @param lastDirectionTaken 
     */
    public void addChild(Nodo child, int position) {
        switch (position){
            case 0:
                this.hijoUP = child;
                break;
            case 1:
                this.hijoRIGHT = child;
                break;
             case 2:
                this.hijoDOWN = child;
                break;
             case 3:
                this.hijoLEFT = child;
                break;
        }
    }
    
    
    public int[] getRandomChildPosition(int agentOrientation) {
        
        ArrayList<Nodo> childs = new ArrayList<>();
        
        if(hijoUP != null)
            childs.add(hijoUP);
        if(hijoDOWN != null)
            childs.add(hijoDOWN);
        if(hijoLEFT != null)
            childs.add(hijoLEFT);
        if(hijoRIGHT != null)
            childs.add(hijoRIGHT);
        
        int myRand = (int)(Math.random() * (childs.size() - 0) + 0);
        
        return childs.get(myRand).getPosicion();
    }
    
    
    public int[] getRandomEmptyChildPosition(int agentOrientation) {
        
        ArrayList<Nodo> childs = new ArrayList<>();
        
        if(hijoUP != null && !hijoUP.isVisitado())
            childs.add(hijoUP);
        if(hijoDOWN != null  && !hijoDOWN.isVisitado())
            childs.add(hijoDOWN);
        if(hijoLEFT != null  && !hijoLEFT.isVisitado())
            childs.add(hijoLEFT);
        if(hijoRIGHT != null  && !hijoRIGHT.isVisitado())
            childs.add(hijoRIGHT);
        
        int myRand = (int)(Math.random() * (childs.size() - 0) + 0);
        
        if(childs.size() == 0)
            return null;
        else
            return childs.get(myRand).getPosicion();
    }
    

    /**
     * Elimina el hijo en la posición indicada.
     * @param lastDirectionTaken 
     */
    void deleteChildAt(int position) {
        switch (position){
            case 0:
                this.hijoUP = null;
                break;
            case 1:
                this.hijoRIGHT = null;
                break;
             case 2:
                this.hijoDOWN = null;
                break;
             case 3:
                this.hijoLEFT = null;
                break;
        }
        
    }

    public Nodo getHijoUP() { return hijoUP; } 
    public void setHijoUP(Nodo hijoUP) { this.hijoUP = hijoUP; } 
    
    public Nodo getHijoDOWN() { return hijoDOWN; } 
    public void setHijoDOWN(Nodo hijoDOWN) { this.hijoDOWN = hijoDOWN; } 
    
    public Nodo getHijoLEFT() { return hijoLEFT; } 
    public void setHijoLEFT(Nodo hijoLEFT) { this.hijoLEFT = hijoLEFT; } 
    
    public Nodo getHijoRIGHT() { return hijoRIGHT; } 
    public void setHijoRIGHT(Nodo hijoRIGHT) { this.hijoRIGHT = hijoRIGHT; }

    public Object clone(){ Object obj=null; try{ obj=super.clone(); }catch(CloneNotSupportedException ex){ System.out.println(" no se puede duplicar"); } return obj; }

    void printPos() {
        System.out.print("["+posicion[0]+","+posicion[1]+"]");
    }

    public ArrayList<Nodo> getSucessors() {
        ArrayList<Nodo> sucesors = new ArrayList<Nodo>();
        
        if(hijoUP != null)
            sucesors.add(hijoUP);
        if(hijoDOWN != null)
            sucesors.add(hijoDOWN);
        if(hijoLEFT != null)
            sucesors.add(hijoLEFT);
        if(hijoRIGHT != null)
            sucesors.add(hijoRIGHT);
        return sucesors;
        
    }
    
    
}
