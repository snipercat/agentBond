/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.agentbond;

import java.util.ArrayList;

/**
 *
 * @author Snipercat
 */
public class SearchNode {
    
    Nodo node;
    private ArrayList<int[]> targetPath = new ArrayList<>();
    
    
    SearchNode(Nodo root) {
        this.node = root;
        //this.node = (Nodo)root.clone();
    }
    
    SearchNode(Nodo hijo, ArrayList<int[]> targetPath, int[] current_path){
        this.node = (Nodo)hijo.clone();
        this.targetPath = (ArrayList<int[]>) targetPath.clone();
        this.targetPath.add(hijo.getPosicion());
        
        
        
    
    }

    public Nodo getNode() {
        return node;
    }
    
    public int[] getPosition(){
        return node.getPosicion();
    }

    public ArrayList<int[]> getTargetPath() {
        return targetPath;
    }
    
    
    
    
    
}

