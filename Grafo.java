/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.agentbond;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author chava_000
 */
public class Grafo {
    HashMap<String,Nodo> nodos;
    
    
    Grafo(){
        this.nodos = new HashMap<String,Nodo>();
    }

    public void agregarNodos(Nodo... nodos){
        for(Nodo nodo:nodos){
            this.nodos.put(nodo.getIdentificador(), nodo);
        }
    
    }
    
    public void enlazar(Nodo padre,Nodo... hijos){
        for(Nodo hijo:hijos){
                hijo.setPadre(padre.getIdentificador());
                this.nodos.get(padre.getIdentificador()).getSucesores().add(hijo);
        }
    }
    
    public void imprimirGrafo(){
        for(Map.Entry<String, Nodo> entry : nodos.entrySet()){
            System.out.println(entry.getValue().toString());
        }
        System.out.println("***************************************");
    }
    
     
}
