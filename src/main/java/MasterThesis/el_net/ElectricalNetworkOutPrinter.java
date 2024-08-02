package MasterThesis.el_net;


import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcType;
import MasterThesis.base.parameters.AppParameters;
import MasterThesis.el_net.InsideCalcContener.DirectMethodInsideCalcEntity;
import MasterThesis.lineType.LineTypeEntity;
import MasterThesis.node.NodeType;
import MasterThesis.tools.PrintUtility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.LongFunction;

import static MasterThesis.data_calc.BaseValues.voltageBase;

public class ElectricalNetworkOutPrinter {

    //region DIRECTION
    public enum DIRECTION {
        FWD("nastepnik"), REV("poprzednik");

        private final String direction;

        DIRECTION(String direction) {
            this.direction = direction;
        }

        public String getDirection() {
            return direction;
        }
    }
    //endregion

    //region LEVELPRINT
    public enum LEVELPRINT {
        HORIZONTAL(1),
        VERTICAL(2);

        final int id;

        LEVELPRINT(int id) {
            this.id = id;
        }

        public static LEVELPRINT valueOF(int id) {
            if (Objects.equals(id, HORIZONTAL.id)) return HORIZONTAL;
            return VERTICAL;
        }

        public int getId() {
            return id;
        }
    }
    //endregion

    AppParameters appParameters = AppParameters.getInstance();

    //region Decimal Format
    private static final DecimalFormat DECIMAL_FORMAT_EXP = new DecimalFormat("0.0E0");
    private static final DecimalFormat DECIMAL_FORMAT_IMMITANCE = new DecimalFormat("0.0000;(0.0000)");
    private static final DecimalFormat DECIMAL_FORMAT_LENGTH = new DecimalFormat("0.00;(0.00)");
    //endregion

    //region getInstance - Singleton
    private static ElectricalNetworkOutPrinter instance;

    ElectricalNetwork elNet = ElectricalNetwork.getInstance();

    private ElectricalNetworkOutPrinter() {
    }

    public static ElectricalNetworkOutPrinter getInstance() {
        if (instance == null) {
            instance = new ElectricalNetworkOutPrinter();
        }
        return instance;
    }
    //endregion

    //region decode Text
    static String decodeText(String input, String encoding) throws IOException {
        return new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(input.getBytes()),
                        Charset.forName(encoding))).readLine();
    }
    //endregion

    //region print NetQuantity
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
                elNet.transformerTypeMap.size());
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
                elNet.lineTypeMap.size());
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
                elNet.nodeMap.size());
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
                elNet.arcMap.size());
        //endregion

        System.out.println(builder);

    }
    //endregion

    //region print LineImmitance
    public void printLineImmitance() {

        System.out.println("\n-------------------------------------");
        System.out.println("------- Line  Immitance -------------");
        System.out.println("-------------------------------------\n");

        for (ArcEntity arc : elNet.arcList) {
            if (arc.getType() == ArcType.LINE) {
//                if (!(Double.compare(arc.getArcLength(), 0.0) == 0)) {
                System.out.printf("%4d-->%-4d ", arc.getStartNode(), arc.getEndNode());
                System.out.printf("%s %-7.2f[m] ", "L:", arc.getArcLength());
                System.out.printf("R:%-8.4f", arc.getResistancePU());
                System.out.printf("X:%-8.4f", arc.getReactancePU());
                System.out.printf("Z:%-8.4f", arc.getImpedancePU());
                System.out.println("[pu]");
//            }
            }
        }

    }

    //endregion

    //region print trafo immitance w [Ω]
    public void printTrafoImmitance(boolean ohm) {

        System.out.println("\n-------------------------------------");
        System.out.println("------- Trafo Immitance -------------");
        System.out.println("-------------------------------------\n");

        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);

        for (ArcEntity arc : elNet.arcList) {
            if (arc.getType() == ArcType.TRANSFORMER) {
                fmt.format("ID:%3d ", arc.getId());
                fmt.format("R:%.4f[Ω] ", arc.getResistance());
                fmt.format("X:%.4f[Ω] ", arc.getReactance());
                fmt.format("Z:%.4f[Ω] ", arc.getImpedance());
                System.out.println(fmt);
                sb.setLength(0);
            }
        }

    }
    //endregion

    //region print Trafo Immitance w [PU]
    public void printTrafoImmitance() {

        System.out.println("\n-------------------------------------");
        System.out.println("------- Trafo Immitance -------------");
        System.out.println("-------------------------------------\n");

        for (ArcEntity arc : elNet.arcList) {
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
     * @see ElectricalNetworkService#nodeNeighborsForwardMapBuild()
     */

    //region print Node Neighbors With Direction
    public void printNodeNeighborsDirection(DIRECTION direction) {

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
                System.out.println("start node number - Start, end node number - End");
                /*elNet.neighborsForwardMap.forEach((node, neighborsIdList) -> {
                            System.out.printf("Start: %d --> ", node);
                            neighborsIdList.forEach(neighborId -> {
                                System.out.printf("End: %d (ID:%d), ",
                                        elNet.arcMap.get(neighborId).getEndNode(), neighborId);
                            });
                            System.out.println();
                        }
                );*/

                elNet.neighborsForwardMap.forEach((node, neighborsIdList) -> {
                            System.out.printf("Start: %d --> ", node);
                            neighborsIdList.forEach(neighborId -> {
                                System.out.printf("%d, ", elNet.arcMap.get(neighborId).getEndNode());
                            });
                            System.out.println();
                        }
                );


                break;
            //endregion

            //region REVERSE
            case REV:
                elNet.neighborsReverseMap.forEach((nodeEnd, neighborsStartIdList) -> {
                    LongFunction<Long> IDtoNodeStartLongFunction = ID -> elNet.arcMap.get(ID).getStartNode();

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
    public void printNodeCurrentPUIterZero() {

        System.out.println("\n----------------------------------------");
        System.out.println("------- NODE CurrentPU Io --------------");
        System.out.println("----------------------------------------\n");

        elNet.nodeEntityList.forEach(nodeEntity -> {
            System.out.printf("%3d: %.4f [PU]%n", nodeEntity.getId(), nodeEntity.getCurrentInitialPU());
//            DECIMAL_FORMAT_EXP.format(nodeEntity.getCurrentInitialPU()));
//            DECIMAL_FORMAT_IMMITANCE.format(nodeEntity.getCurrentInitialPU()));
        });

    }
    //endregion

    //region print Self Conductance
    public void printSelfConductance() {
        System.out.println("\nKonduktancja własna węzłów [pu]: ");
        elNet.nodeMap.forEach((uniqueNodeNum, nodeEntity) -> {
            System.out.println(String.format("%3d ", uniqueNodeNum)
                    + String.format("%(8.4f", nodeEntity.getSelfConductancePU()));
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

        elNet.arcMap.forEach((IDs, arcEntity) -> {

            // IDs - id z mapy łuków
            // arcEntity - encja znajdująca się pod id = IDs
            // pobranie pozycji (tylko) linii w odpowiednim katalogu
            LongFunction<Long> IDtoPosition = ID -> elNet.arcMap.get(ID).getPosition();
            Long pos = IDtoPosition.apply(IDs);

            // pobranie encji linii na podst. jej pozycji w katalogu
            LineTypeEntity lineTypeEntity = elNet.lineTypeMap.get(pos);

            if (arcEntity.getType() == ArcType.LINE) {
                fmt.format("%-31s %d\n", "ID arc: ",
                        IDs);

                fmt.format("%-31s %d\n", "position ID: ",
                        lineTypeEntity.getId());

                fmt.format("%-31s %s\n", "Kind: ",
                        lineTypeEntity.getKind());

                fmt.format("%-31s %s\n", "Type: ",
                        lineTypeEntity.getType());

                fmt.format("%-31s %.2f [kV]\n", "Voltage: ",
                        lineTypeEntity.getVoltage());

                fmt.format("%-31s %.4f\n", "MAIN_STRAND_INTERSECTION: ",
                        lineTypeEntity.getMainStrandIntersection());

                fmt.format("%-31s %.4f\n", "COHESIVE_UNIT_RESISTANCE: ",
                        lineTypeEntity.getCohesiveUnitResistance());

                fmt.format("%-31s %.4f\n", "ZERO_UNIT_RESISTANCE: ",
                        lineTypeEntity.getZeroUnitResistance());

                fmt.format("%-31s %.4f\n", "COHESIVE_UNIT_REACTANCE: ",
                        lineTypeEntity.getCohesiveUnitReactance());

                fmt.format("%-31s %.4f\n", "ZERO_UNIT_REACTANCE: ",
                        lineTypeEntity.getZeroUnitReactance());

                fmt.format("%-31s %.4f\n", "UNIT_CAPACITANCE_TO_EARTH: ",
                        lineTypeEntity.getUnitCapacitanceToEarth());

                fmt.format("%-31s %.4f\n", "UNIT_WORKING_CAPACITANCE: ",
                        lineTypeEntity.getUnitWorkingCapacitance());

                fmt.format("%-31s %.4f\n", "LONGTERM_LOAD_CAPACITY: ",
                        lineTypeEntity.getLongTermLoadCapacity());

                fmt.format("%-31s %.2f\n", "LONGTERM_SUMMER_LOAD_CAPACITY: ",
                        lineTypeEntity.getLongTermSummerLoadCapacity());

                fmt.format("%-31s %.2f\n", "LONGTERM_WINTER_LOAD_CAPACITY: ",
                        lineTypeEntity.getLongTermWinterLoadCapacity());

                fmt.format("%-31s %.4f\n", "SHORTCIRCUIT_1S_LOAD_CAPACITY: ",
                        lineTypeEntity.getShortCircuit1sLoadCapacity());

                fmt.format("\n\n");
            }
        });

        System.out.println(fmt);

    }
    //endregion

    //region print node values
    public void printNodeValues(LEVELPRINT levelprint) {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        switch (levelprint) {

            //region VERTICAL
            case VERTICAL:
                System.out.println();
                elNet.nodeMap.forEach((aLong, nodeEntity) -> {
                    formatter.format("%-23s %d%n", "id:", nodeEntity.getId());
                    formatter.format("%-23s %.2f [kW]%n", "active power:", nodeEntity.getActivePower());
                    formatter.format("%-23s %.2f [kVar]%n", "reactive power:", nodeEntity.getReactivePower());
                    formatter.format("  %-23s %(.2f [kV]%n", "voltage nominal:", nodeEntity.getNominalVoltage());
                    formatter.format("  %-23s %(.4f%n", "voltage [PU]:", nodeEntity.getVoltagePU());
                    formatter.format("  %-23s %(.4f%n", "voltage real:", nodeEntity.getVoltageReal());
                    formatter.format("%-23s %(.4f%n", "current iter '0' [PU]:", nodeEntity.getCurrentInitialPU());
                    formatter.format("%-23s %(.4f%n", "current [PU]:", nodeEntity.getCurrentPU());
                    formatter.format("%-23s %(.4f%n", "current real:", nodeEntity.getCurrentReal());

                    System.out.println(formatter);
                    sb.setLength(0);
                });

                break;
            //endregion

            //region HORIZONTAL
            case HORIZONTAL:
                System.out.println();
                elNet.nodeMap.forEach((aLong, nodeEntity) -> {
                    formatter.format("%3d ", nodeEntity.getId());
                    formatter.format("%s%-(10.4f ", "Ir: ", nodeEntity.getCurrentReal());
                    formatter.format("%s%-(10.6f ", "Vr: ", nodeEntity.getVoltageReal());

                    System.out.println(formatter);
                    sb.setLength(0);
                });
                break;
            //endregion
        }
    }
    //endregion

    //region arc results print
    public void arcResultsPrint() {
        // ID
        // TYPE
        // TRANSMIT POWER

    }
    //endregion

    //region print distributed nodes
    public void printDistributedNodes(NodeType nodeType) {
        System.out.println("\nDistributed nodes: ");
        elNet.nodeMap.forEach((aLong, nodeEntity) -> {
            if (nodeEntity.getNodeType() == nodeType) {
                System.out.print(aLong + " ");
            }
        });

        System.out.println();
    }
    //endregion

    //region print nodes with no neighbors in front
    public void printNodesWithNoNeighborsInFront() {
        System.out.println("\nnodes that have no neighbors in front: ");
        elNet.nodesWithNoNeighborsInFrontList.forEach(aLong ->
                System.out.println(aLong + " "));
    }
    //endregion

    //region print nodes with no neighbors at back
    public void printNodesWithNoNeighborsAtBack() {
        System.out.println("\nPower nodes: ");
        elNet.nodesWithNoNeighborsBackList.forEach(System.out::println);
    }
    //endregion

    //region print nodes neighbors forward and reverse map
    public void printNodesNeighborsForwardReverseMap() {
        System.out.println("\nmap of the neighbors of the nodes in front and behind:\n");
        if (!elNet.nodesNeighborsForwardReverseMap.isEmpty()) {
            elNet.nodesNeighborsForwardReverseMap.forEach((uniqueNodeNumber, neighborsIDsList) -> {
                System.out.printf("%3d ", uniqueNodeNumber);
                System.out.println(neighborsIDsList);
            });
        } else {
            System.out.println("Mapa: nodesNeighborsForwardReverseMap jest pusta");
        }
        System.out.println();
    }
    //endregion

    //region print interim calculations
    public void printInterimCalculations() {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        String arrowSpacer = " <<<<<<<<<<<<<<<<<<<";
        String lineSpacer = "---------------------------";

        DecimalFormat df = new DecimalFormat("0.0000##;(#,0000##)");
//        DecimalFormat df = new DecimalFormat("0.0E0;(#,##0.0#)");

        Set<Map.Entry<Long, Map<Long, DirectMethodInsideCalcEntity>>> entrySet = elNet.everyIterateCalcMap.entrySet();
        ArrayList<Long> longArrayList = new ArrayList<>(elNet.everyIterateCalcMap.keySet());
        Collections.sort(longArrayList);

        elNet.everyIterateCalcMap.forEach((iterate, nodeInterimMap) -> {

            if (iterate.equals(Collections.max(longArrayList))) {

                formatter.format("\nk:%d - pętla do-while%s\n\n",
                        iterate, arrowSpacer);

                nodeInterimMap.forEach((node, DirectMethodInsideCalcEntity) -> {

                    formatter.format("%s\n%-18s [%d]\n%s\n",
                            lineSpacer, "node", node, lineSpacer);

                    formatter.format("%-18s %s\n", "currentPU: ",
                            df.format(DirectMethodInsideCalcEntity.getCurrentPU()));

                    formatter.format("%-18s %s\n", "Ui: ",
                            df.format(DirectMethodInsideCalcEntity.getU_i()));

                    formatter.format("%-18s %s\n", "Gii: ",
                            df.format(DirectMethodInsideCalcEntity.getG_ii()));

                    formatter.format("%-18s %s\n", "currentPU: ",
                            df.format(DirectMethodInsideCalcEntity.getItem_Ii()));

                    formatter.format("%-18s %s\n", "Ui * Gii:",
                            df.format(DirectMethodInsideCalcEntity.getItem_Ui_Gii()));

                    formatter.format("%-19s %s\n", "ΔI:", df.format(DirectMethodInsideCalcEntity.getDeltaCurrentThisIterPresentNode()));

                    formatter.format("%-19s %s\n", "ΔVi",
                            df.format(DirectMethodInsideCalcEntity.getDeltaVoltageThisIterPresentNode()));

                    formatter.format("%-18s %s\n", "V iter [PU]: ",
                            df.format(DirectMethodInsideCalcEntity.getVoltageThisIterPresentNode()));

                    formatter.format("%-18s %.4f\n", "V iter real: ",
                            (DirectMethodInsideCalcEntity.getVoltageThisIterPresentNode() * voltageBase));

//                    formatter.format("%-18s %s\n",
//                            "V iter real: ", df.format(InsideLoopEveryNodeCalcEntity.getVoltageThisIterPresentNode()
//                                    * voltageBase));

                    formatter.format("%-18s %d\n", "a: ",
                            DirectMethodInsideCalcEntity.getA());

                    System.out.println(sb);
                    sb.setLength(0);

                    DirectMethodInsideCalcEntity.getNeighborCalcMap().forEach((neighbor, neighborCalcEntity) -> {

                        System.out.printf("\t%-12s [%d]\n",
                                "neighbor", neighbor);

                        System.out.printf("\t%-12s %s\n",
                                "Uj", df.format(neighborCalcEntity.getU_j()));

                        System.out.printf("\t%-12s %s\n",
                                "Ui - Uj", df.format(DirectMethodInsideCalcEntity.getU_i() - neighborCalcEntity.getU_j()));

                        System.out.printf("\t%-12s %s\n",
                                "Gij", df.format(neighborCalcEntity.getG_ij()));

                        System.out.printf("\t%-14s %s\n",
                                "Suma: ", df.format(neighborCalcEntity.getItem_sum()));

                        System.out.println();
                    });
                });
            }

        });
    }
    //endregion

}
