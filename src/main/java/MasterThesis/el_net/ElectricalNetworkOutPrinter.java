package MasterThesis.el_net;


import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcType;
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
    private static final DecimalFormat DECIMAL_FORMAT_LINE =
            new DecimalFormat("0.0#E0#");

    private static final DecimalFormat DECIMAL_FORMAT_CURRENT_PU =
            new DecimalFormat("0.00##E0");

    private static final DecimalFormat DECIMAL_FORMAT_TRAFO =
            new DecimalFormat("0.00##E0");

    private static final DecimalFormat DECIMAL_FORMAT_TRAFO_VOLTAGE =
            new DecimalFormat("0.00E0");

    private static final DecimalFormat DECIMAL_FORMAT_NODE_VOLTAGE_PU =
            new DecimalFormat("0.00E0");
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

    //region decodeText
    static String decodeText(String input, String encoding) throws IOException {
        return
                new BufferedReader(
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

    //region printLineImmitance
    public void printLineImmitance() {

        System.out.println("\n-------------------------------------");
        System.out.println("------- Line  Immitance -------------");
        System.out.println("-------------------------------------\n");


        for (ArcEntity arc : elNet.arcList) {
            if (arc.getType() == ArcType.LINE) {
                System.out.println(arc.getId() + " | " +
                        "L: " + arc.getArcLength() + " | " +
                        "X: " + DECIMAL_FORMAT_LINE.format(arc.getReactance()) + " | " +
                        "R: " + DECIMAL_FORMAT_LINE.format(arc.getResistance()) + " | " +
                        "Z: " + DECIMAL_FORMAT_LINE.format(arc.getImpedance())

                );

            }
        }
    }
    //endregion

    //region printTrafoImmitance
    public void printTrafoImmitance() {

        System.out.println("\n-------------------------------------");
        System.out.println("------- Trafo Immitance -------------");
        System.out.println("-------------------------------------\n");

        for (ArcEntity arc : elNet.arcList) {
            if (arc.getType() == ArcType.TRANSFORMER) {

                StringBuilder stringBuilder = new StringBuilder();
                Formatter formatter = new Formatter(stringBuilder);

                formatter.format("--------- Trafo ID: (" + arc.getId() + ") ---------\n");

                formatter.format("%-30s %s%n", "(R) Resistance [pu]:",
                        DECIMAL_FORMAT_TRAFO.format(arc.getResistancePU()));

                formatter.format("%-30s %s%n", "(X) Reactance [pu]:",
                        DECIMAL_FORMAT_TRAFO.format(arc.getReactancePU()));

                formatter.format("%-30s %s%n", "(Z) Impedance [pu]:",
                        DECIMAL_FORMAT_TRAFO.format(arc.getImpedancePU()));

                formatter.format("%-30s %s%n", "(H) UPPER_VOLTAGE [pu]:",
                        DECIMAL_FORMAT_TRAFO_VOLTAGE.format(arc.getVoltageHighPU()));

                formatter.format("%-30s %s%n", "(L) LOWER_VOLTAGE [pu]:",
                        DECIMAL_FORMAT_TRAFO_VOLTAGE.format(arc.getVoltageLowPU()));

                System.out.println(stringBuilder);

            }
        }
    }
    //endregion

    //region printNodeVoltagePu
    public void printNodeVoltagePu() {

        System.out.println("\n-------------------------------------");
        System.out.println("------- Node VoltagePu --------------");
        System.out.println("-------------------------------------\n");

        elNet.nodeList.forEach(nodeEntity ->
                System.out.printf("%3d: %s%n",
                        nodeEntity.getId(),
                        DECIMAL_FORMAT_NODE_VOLTAGE_PU.format(nodeEntity.getVoltagePU())));
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
     * @see ElectricalNetworkService#nodeNeighborsFollowingListBuild()
     */

    //region printNodeNeighborsWithDirection
    public void printNodeNeighborsWithDirection(DIRECTION direction) {

        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);

        //region filler
        String formattedString = fmt.format("----- Sasiedzi wezlow - " + direction.getDirection() + " -----").toString();

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
            case FOLLOWING:
                elNet.neighborsConsequentMap.forEach((node, neighborsIdList) -> {
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
            case PREDECESSOR:
                elNet.neighborsPredecessorMap.forEach((nodeEnd, neighborsStartIdList) -> {
                    System.out.printf("node: [%3d] --> neighbors: ", nodeEnd);

                    LongFunction<Long> IDtoNodeStartLongFunction = ID ->
                            elNet.arcMap.get(ID).getStartNode();

                    System.out.print("[");
                    for (Long startNodeID : neighborsStartIdList) {
                        System.out.printf("%d ", IDtoNodeStartLongFunction.apply(startNodeID));
                    }
                    System.out.println("]");
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

    //region printNodeCurrentPUIter0
    public void printNodeCurrentPUIter0() {

        System.out.println("\n-------------------------------------");
        System.out.println("------- NODE CurrentPU --------------");
        System.out.println("-------------------------------------\n");

        elNet.nodeList.forEach(nodeEntity -> {
            System.out.printf("%3d: %s%n",
                    nodeEntity.getId(),
                    DECIMAL_FORMAT_CURRENT_PU.format(nodeEntity.getCurrentInitialPU()));
        });

    }
    //endregion

    //region DIRECTION
    public enum DIRECTION {
        FOLLOWING("nastepnik"), PREDECESSOR("poprzednik");

        private final String direction;

        DIRECTION(String direction) {
            this.direction = direction;
        }

        public String getDirection() {
            return direction;
        }
    }
    //endregion
}
