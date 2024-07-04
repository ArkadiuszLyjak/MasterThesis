package MasterThesis.bfs;

public class BfsAlgorithmOutPrinter {

    private static BfsAlgorithmOutPrinter instance;
    BfsAlgorithm bfsAlgorithm = BfsAlgorithm.getInstance();

    //region getInstance - Singleton
    private BfsAlgorithmOutPrinter() {

    }

    public static BfsAlgorithmOutPrinter getInstance() {

        if (instance == null) {
            instance = new BfsAlgorithmOutPrinter();
        }
        return instance;
    }
    //endregion

    //region printNodeVisitedOrder
    public void printNodeVisitedOrder() {

        System.out.println("\n-----------------------------------------");
        System.out.println("------- Kolejnosc odwiedzin -------------");
        System.out.println("-----------------------------------------\n");

        for (Long i = 0L; i <= bfsAlgorithm.getNetLevel(); i++) {
            bfsAlgorithm.arcLevelsMap.get(i).forEach(nodeArcVO ->

                            System.out.println("LEVEL " + nodeArcVO.netLevel + " > "
                                    + nodeArcVO.nodeId + "->" + nodeArcVO.neighborNodeId)
            );
        }

    }
    //endregion

}
