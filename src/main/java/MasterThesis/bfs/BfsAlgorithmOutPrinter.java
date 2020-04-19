package MasterThesis.bfs;

public class BfsAlgorithmOutPrinter {
    private static BfsAlgorithmOutPrinter  instance;
    BfsAlgorithm bfsAlgorithm = BfsAlgorithm.getInstance();

    public static BfsAlgorithmOutPrinter getInstance(){

        if( instance == null){
            instance = new BfsAlgorithmOutPrinter();
        }
        return instance;
    }

    public void printNodeVisitedOrder(){
        System.out.println(" ---------- Kolejnosc odwiedzin -------------");
        for (Long i = 0L; i <= bfsAlgorithm.getNetLevel(); i++) {
            bfsAlgorithm.arcLevelsMap.get(i).forEach(nodeArcVO ->

                            System.out.println("LEVEL " + nodeArcVO.netLevel + "  > " + nodeArcVO.nodeId + "->" +
                                    nodeArcVO.neighborNodeId)
                    //        elNet.arcMap.get(nodeArcVO.arcId).getEndNodeId())
            );
        }

    }


}
