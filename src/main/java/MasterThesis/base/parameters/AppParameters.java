package MasterThesis.base.parameters;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppParameters {
    /***************************************************
    *  Default values
    * */
    String arcFileName             = "Arc.txt";
    String lineTypeFileName        = "Line_Type.txt";
    String nodeFileName            = "Node.txt";
    String transformerTypeFileName = "Transformer_Type.txt";
    String dataArcResultsFileName  = "MO_DATA_ARC_RESULTS.txt";
    String dataNodeResultsFileName = "MO_DATA_NODE_RESULTS.txt";
    String delimiter               = "|";
    String sourceDataFilesPath     = "C:\\repo\\dataFiles\\" ;
    //*******************************
    //SNGLENTON
    private static AppParameters appParameters;
    private AppParameters(){}
    public static AppParameters getInstance(){
        if (appParameters == null){
            appParameters = new AppParameters();
        }
        return appParameters;
    }

    @Override
    public String toString() {
        return "APPLICATION PARAMETERS \n" +
                "---------------------\n"+
                "arcFileName='" + arcFileName + '\'' + "\n"+
                "lineTypeFileName='" + lineTypeFileName + '\'' +"\n"+
                "nodeFileName='" + nodeFileName + '\'' +"\n"+
                "transformerTypeFileName='" + transformerTypeFileName + '\'' +"\n"+
                "dataArcResultsFileName='" + dataArcResultsFileName + '\'' +"\n"+
                "dataNodeResultsFileName='" + dataNodeResultsFileName + '\'' +"\n"+
                "delimiter='" + delimiter + '\'' +"\n"+
                "sourceDataFilesPath='" + sourceDataFilesPath + '\'' +"\n"+
                '}';
    }

    public  String getNodeFileFullPath(){
        return  sourceDataFilesPath+
                nodeFileName ;
    }

    public String getArcFullFileName(){
        return  sourceDataFilesPath+
                arcFileName;

    }
    public String getLineTypeFullFileName(){
        return  sourceDataFilesPath+
                lineTypeFileName;

    }
    public String getNodeFullFileName(){
        return  sourceDataFilesPath+
                nodeFileName;

    }
    public String getTransformerTypeFullFileName(){
        return  sourceDataFilesPath+
                transformerTypeFileName;

    }
    public String getDataArcResultsFullFileName(){
        return  sourceDataFilesPath+
                dataArcResultsFileName;

    }
    public String getDataNodeResultsFullFileName(){
        return  sourceDataFilesPath+
                dataNodeResultsFileName;

    }


}
