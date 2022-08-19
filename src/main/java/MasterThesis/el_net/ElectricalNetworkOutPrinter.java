package MasterThesis.el_net;


import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcType;
import MasterThesis.lineType.LineTypeEntity;
import MasterThesis.node.NodeType;
import MasterThesis.tools.PrintUtility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.function.LongFunction;

public class ElectricalNetworkOutPrinter {

    //region DecimalFormat
    private static final DecimalFormat DECIMAL_FORMAT_EXP = new DecimalFormat("0.00E0");
    //endregion

    //region getInstance - Singleton
    private static ElectricalNetworkOutPrinter instance;

    ElectricalNetwork electricalNetwork = ElectricalNetwork.getInstance();

    private ElectricalNetworkOutPrinter() {
    }

    public static ElectricalNetworkOutPrinter getInstance() {
        if (instance == null) {
            instance = new ElectricalNetworkOutPrinter();
        }
        return instance;
    }
    //endregion

    //region decodeText
    static String decodeText(String input, String encoding) throws IOException {
        return new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(input.getBytes()),
                        Charset.forName(encoding))).readLine();
    }
    //endregion

    //region printNetQuantity
    public void printNetQuantity() throws IOException {

        String longestHeaderString = "-------- Ilosc elementow sieci ---------"; // 39
        int i = longestHeaderString.length();

        String filler = PrintUtility.createExtenderWithCharacter(
                i - 1,
                '-',
                strings -> 0,
                longestHeaderString);

        System.out.println();
        System.out.println(filler);
        System.out.println(longestHeaderString);
        System.out.println(filler);
        System.out.println();

        String trafoString = "Ilosc rodzajow transformatorow: ";
        String lineTypeString = "Ilosc typow linii: ";
        String nodeString = "Ilosc wezlow: ";
        String arcString = "Ilosc lukow: ";

        int longestSizeString = trafoString.codePointCount(0, trafoString.length());
        int fillerLength = longestSizeString + 20;

        StringBuilder builder = new StringBuilder();
        Formatter fmt = new Formatter(builder);

        //region trafoString
        fmt.format("%s %s %d%n", trafoString, PrintUtility.createExtenderWithCharacter(
                        fillerLength,
                        '-',
                        strings -> {
                            int length = 0;
                            for (String s : strings) {
                                length += s.length();
                            }
                            return length;
                        }, trafoString),
                electricalNetwork.transformerTypeMap.size());
        //endregion

        //region lineTypeString
        fmt.format("%s %s %d%n", lineTypeString, PrintUtility.createExtenderWithCharacter(
                        fillerLength,
                        '-',
                        strings -> {
                            int length = 0;
                            for (String s : strings) {
                                length += s.length();
                            }
                            return length;
                        }, lineTypeString),
                electricalNetwork.lineTypeMap.size());
        //endregion

        //region nodeString
        fmt.format("%s %s %d%n", nodeString, PrintUtility.createExtenderWithCharacter(
                        fillerLength,
                        '-',
                        strings -> {
                            int length = 0;
                            for (String s : strings) {
                                length += s.length();
                            }
                            return length;
                        }, nodeString),
                electricalNetwork.nodeMap.size());
        //endregion

        //region arcString
        fmt.format("%s %s %d%n", arcString, PrintUtility.createExtenderWithCharacter(
                        fillerLength,
                        '-',
                        strings -> {
                            int length = 0;
                            for (String s : strings) {
                                length += s.length();
                            }
                            return length;
                        }, arcString),
                electricalNetwork.arcMap.size());
        //endregion

        System.out.println(builder);

    }
    //endregion

    //region printLineImmitance
    public void printLineImmitance() {

        System.out.println("\n-------------------------------------");
        System.out.println("------- Line  Immitance -------------");
        System.out.println("-------------------------------------\n");

        for (ArcEntity arc : electricalNetwork.arcList) {
            if (arc.getType() == ArcType.LINE) {
                System.out.printf("%4d-->%-4d ", arc.getStartNode(), arc.getEndNode());
                System.out.print("L:" + DECIMAL_FORMAT_EXP.format(arc.getArcLength()) + "[m]");
                System.out.print(" R:" + DECIMAL_FORMAT_EXP.format(arc.getResistancePU()));
                System.out.print(" X:" + DECIMAL_FORMAT_EXP.format(arc.getReactancePU()));
                System.out.print(" Z:" + DECIMAL_FORMAT_EXP.format(arc.getImpedancePU()));
                System.out.println(" [pu]");


            }
        }

    }
    //endregion

    //region printTrafoImmitance
    public void printTrafoImmitance() {

        System.out.println("\n-------------------------------------");
        System.out.println("------- Trafo Immitance -------------");
        System.out.println("-------------------------------------\n");

        for (ArcEntity arc : electricalNetwork.arcList) {
            if (arc.getType() == ArcType.TRANSFORMER) {
                System.out.printf("%s %d %s", "--------- Trafo ID: (", arc.getId(), ") ---------\n");
                System.out.printf("%-25s %4.2e %s%n", "(R) Resistance:", arc.getResistancePU(), " [pu]");
                System.out.printf("%-25s %4.2e %s%n", "(X) Reactance:", arc.getReactancePU(), " [pu]");
                System.out.printf("%-25s %4.2e %s%n", "(Z) Impedance:", arc.getImpedancePU(), " [pu]");
                System.out.printf("%-25s %4.2e %s%n", "(H) UPPER_VOLTAGE:", arc.getVoltageHighPU(), " [pu]");
                System.out.printf("%-25s %4.2e %s%n", "(L) LOWER_VOLTAGE:", arc.getVoltageLowPU(), " [pu]");
                System.out.println();
            }
        }

    }
    //endregion

    //region printNodeVoltagePu
    public void printNodeInfo() {

        System.out.println("\n-------------------------------------");
        System.out.println("------- Node Information --------------");
        System.out.println("-------------------------------------\n");

        electricalNetwork.nodeList.forEach(nodeEntity -> {
            System.out.println(nodeEntity.toString());
        });
    }
    //endregion

    /**
     * <p>Metoda zwraca <em><i>unikalny numer węzła</i></em> oraz listę jego sąsiadów.
     * Lista sąsiadów jest tego samego typu co <tt>unikalny</tt> nr węzła.
     * Węzły-sąsiedzi są w postaci ID, które służy do odszyfrowania
     * z encji łuku odpowiedniego numeru węzła-sąsiada.</p>
     *
     * <p>This is a simple description of the method. . .
     * <a href="http://www.supermanisthegreatest.com">Superman!</a> </p>
     *
     * @see ElectricalNetwork#arcMap
     * @see ElectricalNetworkService#nodeNbrsFwdListBuild()
     */

    //region printNodeNeighborsWithDirection
    public void printNodeNBRSdirection(DIRECTION direction) {

        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);

        //region filler
        String formattedString = fmt
                .format("----- Sasiedzi wezlow - " + direction.getDirection() + " -----")
                .toString();

        System.out.print(PrintUtility.createExtenderWithCharacter(
                formattedString.length() - 1,
                '-',
                strings -> 0, formattedString) + "\n");

        System.out.println(formattedString);

        System.out.print(PrintUtility.createExtenderWithCharacter(
                formattedString.length() - 1,
                '-',
                strings -> 0, formattedString) + "\n");

        //endregion

        switch (direction) {
            //region FORWARD
            case FWD:
                electricalNetwork.neighborsFORWARDmap.forEach((node, neighborsIdList) -> {
                            System.out.printf("node:[%3d] --> ", node);
                            System.out.print("neighbors: [");
                            neighborsIdList.forEach(neighborId -> {
                                System.out.printf("%d ", electricalNetwork.arcMap.get(neighborId).getEndNode());
                            });
                            System.out.println("]");
                        }
                );

                break;
            //endregion

            //region REVERSE
            case REVERSE:
                electricalNetwork.neighborsREVERSEMap.forEach((nodeEnd, neighborsStartIdList) -> {
                    LongFunction<Long> IDtoNodeStartLongFunction = ID ->
                            electricalNetwork.arcMap.get(ID).getStartNode();

                    System.out.print("reverse neighbors: ");
                    for (Long startNodeID : neighborsStartIdList) {
                        System.out.printf("%d ", IDtoNodeStartLongFunction.apply(startNodeID));
                    }
                    System.out.println("<-- " + nodeEnd);
                });
                break;
            //endregion

        }
    }
    //endregion

    /**
     * <p>Prąd w iteracji zerowej.<br></p>
     * <p>Prąd w każdym węźle jest sumą prądów liczonych
     * "po jego sąsiadach" wg <b>{@code pierwszego prawa prądowego Kirchhoffa}</b>, mówiącego, że
     * prąd <tt><i><b>wpływający</b></i></tt> do węzła jest równy sumie prądów
     * <tt><i><b>wypływająych</b></i></tt> z węzła.</p>
     */

    //region print Node Current PU Iter 0
    public void printNodeCurrPUIterZero() {

        System.out.println("\n----------------------------------------");
        System.out.println("------- NODE CurrentPU Io --------------");
        System.out.println("----------------------------------------\n");

        electricalNetwork.nodeList.forEach(nodeEntity -> {
            System.out.printf("%3d: %s%n",
                    nodeEntity.getId(),
                    DECIMAL_FORMAT_EXP.format(nodeEntity.getCurrentInitialPU()));
        });

    }
    //endregion

    //region DIRECTION
    public enum DIRECTION {
        FWD("nastepnik"), REVERSE("poprzednik");

        private final String direction;

        DIRECTION(String direction) {
            this.direction = direction;
        }

        public String getDirection() {
            return direction;
        }
    }
    //endregion

    //region printSelfConductance
    public void printSelfConductance() {
        System.out.println("\nKonduktancja własna węzłów [pu]: ");
        electricalNetwork.nodeMap.forEach((uniqueNodeNum, nodeEntity) -> {
            System.out.println(String.format("%3d ", uniqueNodeNum)
                            + String.format("%.2e", nodeEntity.getSelfConductance()));
        });

        System.out.println();

    }
    //endregion

    //region print LineType
    public void printLineType() {
        System.out.println("\n----------------------------------------");
        System.out.println("------------- Line Type ----------------");
        System.out.println("----------------------------------------\n");

        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);

        electricalNetwork.arcMap.forEach((IDs, arcEntity) -> {

            // IDs - id z mapy łuków
            // arcEntity - encja znajdująca się pod id = IDs
            // pobranie pozycji (tylko) linii w odpowiednim katalogu
            LongFunction<Long> IDtoPosition = ID -> electricalNetwork.arcMap.get(ID).getPosition();
            Long pos = IDtoPosition.apply(IDs);

            // pobranie encji linii na podst. jej pozycji w katalogu
            LineTypeEntity lineTypeEntity = electricalNetwork.lineTypeMap.get(pos);

            if (arcEntity.getType() == ArcType.LINE) {
                fmt.format("%-31s %d\n", "ID arc: ", IDs);
                fmt.format("%-31s %d\n", "position ID: ", lineTypeEntity.getId());
                fmt.format("%-31s %s\n", "Kind: ", lineTypeEntity.getKind());
                fmt.format("%-31s %s\n", "Type: ", lineTypeEntity.getType());
                fmt.format("%-31s %.2f [kV]\n", "Voltage: ", lineTypeEntity.getVoltage());
                fmt.format("%-31s %.2f\n", "MAIN_STRAND_INTERSECTION: ", lineTypeEntity.getMainStrandIntersection());
                fmt.format("%-31s %.2f\n", "COHESIVE_UNIT_RESISTANCE: ", lineTypeEntity.getCohesiveUnitResistance());
                fmt.format("%-31s %.2f\n", "ZERO_UNIT_RESISTANCE: ", lineTypeEntity.getZeroUnitResistance());
                fmt.format("%-31s %.2f\n", "COHESIVE_UNIT_REACTANCE: ", lineTypeEntity.getCohesiveUnitReactance());
                fmt.format("%-31s %.2f\n", "ZERO_UNIT_REACTANCE: ", lineTypeEntity.getZeroUnitReactance());
                fmt.format("%-31s %.2f\n", "UNIT_CAPACITANCE_TO_EARTH: ", lineTypeEntity.getUnitCapacitanceToEarth());
                fmt.format("%-31s %.2f\n", "UNIT_WORKING_CAPACITANCE: ", lineTypeEntity.getUnitWorkingCapacitance());
                fmt.format("%-31s %.2f\n", "LONGTERM_LOAD_CAPACITY: ", lineTypeEntity.getLongTermLoadCapacity());
                fmt.format("%-31s %.2f\n", "LONGTERM_SUMMER_LOAD_CAPACITY: ", lineTypeEntity.getLongTermSummerLoadCapacity());
                fmt.format("%-31s %.2f\n", "LONGTERM_WINTER_LOAD_CAPACITY: ", lineTypeEntity.getLongTermWinterLoadCapacity());
                fmt.format("%-31s %.2f\n", "SHORTCIRCUIT_1S_LOAD_CAPACITY: ", lineTypeEntity.getShortCircuit1sLoadCapacity());
                fmt.format("\n\n");
            }
        });

        System.out.println(fmt.toString());

    }
    //endregion

    //region print distributed nodes
    public void printDistributedNodes(NodeType nodeType) {
        System.out.println("\nDistributed nodes: ");
        electricalNetwork.nodeMap.forEach((aLong, nodeEntity) -> {
            if (nodeEntity.getNodeType() == nodeType) {
                System.out.print(aLong + " ");
            }
        });

        System.out.println();
    }
    //endregion

    //region print nodes with no neighbors in front
    public void printNDSwithNoNBRSinFront() {
        System.out.println("\nNodes with no neighbors: ");
        electricalNetwork.nodesWithNoNeighborsInFront.forEach(aLong -> System.out.print(aLong + " "));
        System.out.println();
    }
    //endregion

    public void printNodesNbrsFwdRevMap() {
        System.out.println("Mapa sąsiadów węzłów FWD i REV:");
        if (!electricalNetwork.nodesNBRfwdREVmap.isEmpty()) {
            electricalNetwork.nodesNBRfwdREVmap.forEach((uniqueNodeNumber, neighborsIDsList) -> {
                /*//region zamiana ID węzła na unikalny nr węzła
                ArrayList<Long> nodeUniqueNumber = new ArrayList<>();
                for (Long nodeID : neighborsIDsList) {
                    nodeUniqueNumber.add(electricalNetwork.arcMap.get(nodeID).getStartNode());
                }
                System.out.println(uniqueNodeNumber + ":" + nodeUniqueNumber);
                //endregion*/

                // drukuje unikalny nr węzła oraz tablicę ID sąsiadów w przód i tył
                System.out.println(uniqueNodeNumber + ": ID's " + neighborsIDsList);
            });
        } else {
            System.out.println("Mapa: nodesNBRfwdREVmap jest pusta");
        }
    }
}
