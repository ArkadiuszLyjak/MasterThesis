package MasterThesis.arc;

import MasterThesis.base.parameters.AppParametersService;

//2|2|4|LINE|8|9000|1|1|2
//11|3|11|LINE|1|76.28|1|1|2
//12|3|12|LINE|1|325.9|1|1|2


public class ArcFactory {

    //region prepareFromString
    public static ArcEntity prepareFromString(String arcStr) {

        String[] entityArray = arcStr.split(AppParametersService.getInstance().getRegex());

        ArcEntity entity = new ArcEntity(Long.decode(entityArray[0]));

        //Pocz //TODO Powinno wskazywać na obiekt Node
        entity.setStartNode(Long.valueOf(entityArray[1]));

        //Konc   //TODO Powinno wskazywać na obiekt Node
        entity.setEndNode(Long.valueOf(entityArray[2]));

        entity.setType(ArcType.valueOf(entityArray[3]));            //Typ
        entity.setPosition(Long.valueOf(entityArray[4]));           //Pozycja
        entity.setArcLength(Double.valueOf(entityArray[5]));        //Dlugosc
        entity.setTracks(Integer.valueOf(entityArray[6]));          //Tory
        entity.setCondition(Integer.valueOf(entityArray[7]));       //Stan
        entity.setChange(Integer.valueOf(entityArray[8]));          //Zmiana

        return entity;
    }
    //endregion

}