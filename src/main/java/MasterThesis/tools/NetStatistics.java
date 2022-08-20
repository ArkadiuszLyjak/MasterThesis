package MasterThesis.tools;

public class NetStatistics {
    private static final NetStatistics INSTANCE =  new NetStatistics();

    private NetStatistics() {

    }

    public static NetStatistics getInstance() {
        return INSTANCE;
    }
}
