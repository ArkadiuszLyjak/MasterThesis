package MasterThesis.base.parameters;


public class App_Parameters_Service {

    //region getInstance Singleton
    private static App_Parameters_Service instance;
    App_Parameters params = App_Parameters.getInstance();

    private App_Parameters_Service() {
    }

    public static App_Parameters_Service getInstance() {
        if (instance == null) {
            instance = new App_Parameters_Service();
        }
        return instance;
    }
    //endregion

    //region getRegex
    public String getRegex() {
        return "\\" + params.getDelimiter();
    }
    //endregion

    //region setParametersFromArgs
    public void setParametersFromArgs(String[] args) {
        if (args.length > 0) {
//            for (int i = 0; ) System.out.println(args[0]);
            params.setSourceDataFilesPath(args[1]);
        }
    }
    //endregion
}
