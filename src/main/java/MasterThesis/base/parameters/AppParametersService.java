package MasterThesis.base.parameters;


public class AppParametersService {
    AppParameters params = AppParameters.getInstance();
    static AppParametersService instance;

    private AppParametersService(){}

    public static AppParametersService getInstance() {
        if (instance == null){
            instance = new AppParametersService();
        }
        return instance;
    }

    public String getRegex(){
        String regex = "\\" + params.getDelimiter();
        return regex;
    }

    public void setParametersFromArgs(String[] args) {

        if (args.length > 0 ) {
        //for (int i=0;  )
          //  System.out.println(args[0]);
            params.setSourceDataFilesPath(args[1]);
        }
    }
}
