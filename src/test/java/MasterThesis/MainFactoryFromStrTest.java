package MasterThesis;

import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcFactory;
import MasterThesis.base.parameters.AppParameters;
import MasterThesis.lineType.LineTypeEntity;
import MasterThesis.lineType.LineTypeFactory;
import MasterThesis.node.NodeDecorator;
import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeFactory;
import MasterThesis.transformer_type.TransformerTypeEntity;
import MasterThesis.transformer_type.TransformerTypeFactory;

import java.util.ArrayList;
import java.util.List;

public class MainFactoryFromStrTest {

    public static void main(String[] args) {
        System.out.println("Zaczynamy");
        System.out.println(args.length);
        if (args.length > 0) {
            System.out.println(args[0]);
            System.out.println(args[1]);
        }
        System.out.println("--------------------------------");
        AppParameters parameters = AppParameters.getInstance();
        System.out.println(parameters);

        //----------------
        NodeDecorator nodeDecorator = new NodeDecorator();
        List<NodeEntity> nodeList = new ArrayList<>();
        nodeList.add(NodeFactory.prepareFromString("105|1|40|47.058|0|0|0.4"));
        nodeList.add(NodeFactory.prepareFromString("106|4|40|47.058|0|0|0.4"));

        for (NodeEntity node : nodeList) {
            System.out.println(nodeDecorator.getLabel(node));
        }
        //----------------

        List<TransformerTypeEntity> transformerTypeList = new ArrayList<>();

        transformerTypeList.add(TransformerTypeFactory.prepareFromString("10|TOd|630.00|15.75|0.42|0|0|0|0|6.5|0.6|0|6|0|0"));
        transformerTypeList.add(TransformerTypeFactory.prepareFromString("20|TOd|800.00|15.75|0.42|0|0|0|0|6.5|0.6|0|6|0|0"));
        for (TransformerTypeEntity transformerType : transformerTypeList) {
            System.out.println(transformerType.getId());
        }
        //----------------
        List<LineTypeEntity> lineTypeList = new ArrayList<>();
        lineTypeList.add(LineTypeFactory.prepareFromString("2|1|5xYKY1x400|0.4|400|0.153|0|0.110|0|0.210|0.37|353|0|0|0"));
        lineTypeList.add(LineTypeFactory.prepareFromString("3|1|5xYKY1x500|0.4|500|0.153|0|0.107|0|0.225|0.40|399|0|0|0"));
        for (LineTypeEntity lineType : lineTypeList) {
            System.out.println(lineType.getType() + " " + lineType.getKind()
            );
        }
        //----------------
        //2|2|4|LINE|8|9000|1|1|2
        //11|3|11|LINE|1|76.28|1|1|2
        //12|3|12|LINE|1|325.9|1|1|2
        List<ArcEntity> arcEntityList = new ArrayList<>();
        arcEntityList.add(ArcFactory.prepareFromString("11|3|11|LINE|1|76.28|1|1|2"));
        arcEntityList.add(ArcFactory.prepareFromString("12|3|12|LINE|1|325.9|1|1|2"));
        arcEntityList.forEach(arcEntity -> System.out.println(arcEntity.getType()));
    }
}

