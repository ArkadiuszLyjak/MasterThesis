package MasterThesis;

import MasterThesis.node.NodeDecorator;
import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FactoryFromStrTest {
    @Test public void printNode(){
        NodeDecorator nodeDecorator = new NodeDecorator();
        List<NodeEntity> nodeList = new ArrayList<>();
        nodeList.add(NodeFactory.prepareFromString("105|1|40|47.058|0|0|0.4"));
        nodeList.add(NodeFactory.prepareFromString("106|4|40|47.058|0|0|0.4"));

        for(NodeEntity node : nodeList){
            System.out.println(nodeDecorator.getLabel(node));
        }

    }
}
