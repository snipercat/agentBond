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
public class SearchNode {
    
    Node node;
    private ArrayList<int[]> targetPath = new ArrayList<>();
    
    
    SearchNode(Node root) {
        this.node = root;
        //this.node = (Nodo)root.clone();
    }
    
    SearchNode(Node child, ArrayList<int[]> targetPath, int[] current_path){
        this.node = child;
        this.targetPath = (ArrayList<int[]>) targetPath.clone();
        this.targetPath.add(child.getPosition());
    }

    public Node getNode() {
        return node;
    }
    
    public int[] getPosition(){
        return node.getPosition();
    }

    public ArrayList<int[]> getTargetPath() {
        return targetPath;
    }
    
    
    
    
    
}

