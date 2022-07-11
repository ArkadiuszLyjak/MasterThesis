package MasterThesis.base.parameters;


public class AppParametersService {

    //region getInstance Singleton
    private static AppParametersService instance;
    AppParameters params = AppParameters.getInstance();

    private AppParametersService() {
    }

    public static AppParametersService getInstance() {
        if (instance == null) {
            instance = new AppParametersService();
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
