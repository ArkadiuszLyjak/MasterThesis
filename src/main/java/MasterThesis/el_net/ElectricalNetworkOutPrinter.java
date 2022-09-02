package MasterThesis.el_net;


import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcType;
import MasterThesis.base.entity.BaseEntity;
import MasterThesis.base.parameters.AppParameters;
import MasterThesis.lineType.LineTypeEntity;
import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeType;
import MasterThesis.tools.PrintUtility;
import MasterThesis.transformer_type.TransformerTypeEntity;
import org.omg.PortableInterceptor.ACTIVE;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.LongFunction;

public class ElectricalNetworkOutPrinter {

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
    private static final DecimalFormat DECIMAL_FORMAT_EXP = new DecimalFormat("0.00E0");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.0000");
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
                System.out.print("L:" + DECIMAL_FORMAT_EXP.format(arc.getArcLength()) + "[m]");
                System.out.print(" R:" + DECIMAL_FORMAT_EXP.format(arc.getResistancePU()));
                System.out.print(" X:" + DECIMAL_FORMAT_EXP.format(arc.getReactancePU()));
                System.out.print(" Z:" + DECIMAL_FORMAT_EXP.format(arc.getImpedancePU()));
                System.out.println(" [pu]");
//                }

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

    //region print Node Voltage [PU]
    public void printNodeInfo() {

        System.out.println("\n-------------------------------------");
        System.out.println("------- Node Information --------------");
        System.out.println("-------------------------------------\n");

        elNet.nodeList.forEach(nodeEntity -> {
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
     * @see ElectricalNetworkService#nodeNeighborsForwardListBuild()
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
                elNet.neighborsForwardMap.forEach((node, neighborsIdList) -> {
                            System.out.printf("node:[%3d] --> ", node);
                            System.out.print("neighbors: [");
                            neighborsIdList.forEach(neighborId -> {
                                System.out.printf("%d ", elNet.arcMap.get(neighborId).getEndNode());
                            });
                            System.out.println("]");
                        }
                );

                break;
            //endregion

            //region REVERSE
            case REV:
                elNet.neighborsReverseMap.forEach((nodeEnd, neighborsStartIdList) -> {
                    LongFunction<Long> IDtoNodeStartLongFunction = ID ->
                            elNet.arcMap.get(ID).getStartNode();

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

        elNet.nodeList.forEach(nodeEntity -> {
            System.out.printf("%3d: %s [PU]%n",
                    nodeEntity.getId(),
                    DECIMAL_FORMAT.format(nodeEntity.getCurrentInitialPU()));
        });

    }
    //endregion

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

    //region print Self Conductance
    public void printSelfConductance() {
        System.out.println("\nKonduktancja własna węzłów [pu]: ");
        elNet.nodeMap.forEach((uniqueNodeNum, nodeEntity) -> {
            System.out.println(String.format("%3d ", uniqueNodeNum)
                    + String.format("%(.2f", nodeEntity.getSelfConductancePU()));
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

                fmt.format("%-31s %.2f\n", "MAIN_STRAND_INTERSECTION: ",
                        lineTypeEntity.getMainStrandIntersection());

                fmt.format("%-31s %.2f\n", "COHESIVE_UNIT_RESISTANCE: ",
                        lineTypeEntity.getCohesiveUnitResistance());

                fmt.format("%-31s %.2f\n", "ZERO_UNIT_RESISTANCE: ",
                        lineTypeEntity.getZeroUnitResistance());

                fmt.format("%-31s %.2f\n", "COHESIVE_UNIT_REACTANCE: ",
                        lineTypeEntity.getCohesiveUnitReactance());

                fmt.format("%-31s %.2f\n", "ZERO_UNIT_REACTANCE: ",
                        lineTypeEntity.getZeroUnitReactance());

                fmt.format("%-31s %.2f\n", "UNIT_CAPACITANCE_TO_EARTH: ",
                        lineTypeEntity.getUnitCapacitanceToEarth());

                fmt.format("%-31s %.2f\n", "UNIT_WORKING_CAPACITANCE: ",
                        lineTypeEntity.getUnitWorkingCapacitance());

                fmt.format("%-31s %.2f\n", "LONGTERM_LOAD_CAPACITY: ",
                        lineTypeEntity.getLongTermLoadCapacity());

                fmt.format("%-31s %.2f\n", "LONGTERM_SUMMER_LOAD_CAPACITY: ",
                        lineTypeEntity.getLongTermSummerLoadCapacity());

                fmt.format("%-31s %.2f\n", "LONGTERM_WINTER_LOAD_CAPACITY: ",
                        lineTypeEntity.getLongTermWinterLoadCapacity());

                fmt.format("%-31s %.2f\n", "SHORTCIRCUIT_1S_LOAD_CAPACITY: ",
                        lineTypeEntity.getShortCircuit1sLoadCapacity());

                fmt.format("\n\n");
            }
        });

        System.out.println(fmt.toString());

    }
    //endregion

    //region print node values
    public void printNodeValues(Map<Long, NodeEntity> nodeEntityMap, LEVELPRINT levelprint) {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        switch (levelprint) {

            case VERTICAL:
                nodeEntityMap.forEach((aLong, nodeEntity) -> {
                    formatter.format("%-23s %d%n", "id:", nodeEntity.getId());
                    formatter.format("%-23s %.2f [kW]%n", "active power:", nodeEntity.getActivePower());
                    formatter.format("%-23s %.2f [kVar]%n", "reactive power:", nodeEntity.getReactivePower());
                    formatter.format("%-23s %.2f [kV]%n", "voltage:", nodeEntity.getNominalVoltage());
                    formatter.format("%-23s %.4f%n", "voltage [PU]:", nodeEntity.getVoltagePU());
                    formatter.format("%-23s %.4f%n", "current iter '0' [PU]:", nodeEntity.getCurrentInitialPU());
                    formatter.format("%-23s %.4f%n", "current [PU]:", nodeEntity.getCurrentPU());
                    formatter.format("%-23s %.4f%n", "voltage real:", nodeEntity.getVoltageReal());
                    formatter.format("%-23s %.4f%n", "current real:", nodeEntity.getCurrentReal());

                    System.out.println(formatter);
                    sb.setLength(0);
                });

                break;

            case HORIZONTAL:
                nodeEntityMap.forEach((aLong, nodeEntity) -> {
                    formatter.format("%s%3d  ", "id:", nodeEntity.getId());
                    formatter.format("%s%-(10.4f ", "I:", nodeEntity.getCurrentReal());
                    formatter.format("%s%-(10.6f ", "V:", nodeEntity.getVoltageReal());

                    System.out.println(formatter);
                    sb.setLength(0);
                });

                break;

        }

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
        elNet.nodesWithNoNeighborsInFront.forEach(aLong -> System.out.print(aLong + " "));
        System.out.println();
    }
    //endregion

    //region print nodes neighbors forward and reverse map
    public void printNodesNeighborsForwardReverseMap() {
        System.out.println("map of the neighbors of the nodes in front and behind:");
        if (!elNet.nodesNeighborsForwardReverseMap.isEmpty()) {
            elNet.nodesNeighborsForwardReverseMap.forEach((uniqueNodeNumber, neighborsIDsList) -> {

                /*//region zamiana ID węzła na unikalny nr węzła
                ArrayList<Long> nodeUniqueNumber = new ArrayList<>();
                for (Long nodeID : neighborsIDsList) {
                    nodeUniqueNumber.add(electricalNetwork.arcMap.get(nodeID).getStartNode());
                }
                System.out.println(uniqueNodeNumber + ":" + nodeUniqueNumber);
                //endregion*/

                // drukuje unikalny nr węzła oraz tablicę ID sąsiadów w przód i tył
                System.out.printf("%3d <-> ", uniqueNodeNumber);
                System.out.println(neighborsIDsList);
            });
        } else {
            System.out.println("Mapa: nodesNBRfwdREVmap jest pusta");
        }
    }
    //endregion

    //region print "files data stored in maps" using toString method
    public void arcEntityPrinter(Map<Long, ArcEntity> map) {
        map.forEach(biConsumer);
    }

    public void lineTypeEntityPrinter(Map<Long, LineTypeEntity> map) {
        map.forEach(biConsumer);
    }

    public void nodeEntityPrinter(Map<Long, NodeEntity> map) {
        map.forEach(biConsumer);
    }

    public void transformerTypeEntityPrinter(Map<Long, TransformerTypeEntity> map) {
        map.forEach(biConsumer);
    }

    static BiConsumer<Long, BaseEntity> biConsumer = (ID, baseEntity) -> {
        System.out.println(baseEntity.toString());
    };

    BiConsumer<Long, LineTypeEntity> lineTypeBiConsumer = (ID, lineTypeEntity) -> {

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        String del = appParameters.getDelimiter();

        formatter.format("%d%s", lineTypeEntity.getId(), del);
        formatter.format("%d%s", lineTypeEntity.getKind().getId(), del);
        formatter.format("%s%s", lineTypeEntity.getType(), del);
        formatter.format("%.1f%s", lineTypeEntity.getVoltage(), del);
        formatter.format("%.0f%s", lineTypeEntity.getMainStrandIntersection(), del);
        formatter.format("%.3f%s", lineTypeEntity.getCohesiveUnitResistance(), del);
        formatter.format("%.0f%s", lineTypeEntity.getZeroUnitResistance(), del);
        formatter.format("%.3f%s", lineTypeEntity.getCohesiveUnitReactance(), del);
        formatter.format("%.1f%s", lineTypeEntity.getZeroUnitReactance(), del);
        formatter.format("%.3f%s", lineTypeEntity.getUnitCapacitanceToEarth(), del);
        formatter.format("%.2f%s", lineTypeEntity.getUnitWorkingCapacitance(), del);
        formatter.format("%.2f%s", lineTypeEntity.getLongTermSummerLoadCapacity(), del);
        formatter.format("%.2f%s", lineTypeEntity.getLongTermWinterLoadCapacity(), del);
        formatter.format("%.0f%s", lineTypeEntity.getShortCircuit1sLoadCapacity(), del);

        System.out.println(formatter);
    };
    //endregion


    //region arc results print
    public void arcResultsPrint() {
        // ID
        // TYPE
        // TRANSMIT POWER

    }
    //endregion

    //region node results print
    public void nodeResultsPrint() {
        // ID
        // VOLTAGE
    }
    //endregion
}
