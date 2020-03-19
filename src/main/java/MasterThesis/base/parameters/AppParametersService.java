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

    public  String getNodeFileFullPath(){
        return  params.getSourceDataFilesPath()+
                params.getNodeFileName() ;

    }

    public String getArcFullFileName(){
        return  params.getSourceDataFilesPath()+
                params.getArcFileName();

    }
    public String getLineTypeFullFileName(){
        return  params.getSourceDataFilesPath()+
                params.getLineTypeFileName();

    }
    public String getNodeFullFileName(){
        return  params.getSourceDataFilesPath()+
                params.getNodeFileName();

    }
    public String getTransformerTypeFullFileName(){
        return  params.getSourceDataFilesPath()+
                params.getTransformerTypeFileName();

    }
    public String getDataArcResultsFullFileName(){
        return  params.getSourceDataFilesPath()+
                params.getDataArcResultsFileName();

    }
    public String getDataNodeResultsFullFileName(){
        return  params.getSourceDataFilesPath()+
                params.getDataNodeResultsFileName();

    }


}
