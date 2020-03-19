package MasterThesis.arc;

import MasterThesis.base.parameters.AppParametersService;

public class ArcFactory {

    public static ArcEntity prepareFromString(String arcStr) {

        String[] entityArray = arcStr.split(AppParametersService.getInstance().getRegex());

        ArcEntity entity = new ArcEntity(Long.decode(entityArray[0]));
        entity.setStartNode(Long.valueOf(entityArray[1])); //Pocz //TODO Powinno wskazywać na obiekt Node
        entity.setEndNode(Long.valueOf(entityArray[2])); //Konc   //TODO Powinno wskazywać na obiekt Node
        entity.setType(ArcType.valueOf(entityArray[3]));     //Typ
        entity.setPosition(Integer.valueOf(entityArray[4]));      //Pozycja
        entity.setArcLength(Double.valueOf(entityArray[5]));    //Dlugosc
        entity.setLines(Integer.valueOf(entityArray[6]));        //Tory
        entity.setState(Integer.valueOf(entityArray[7]));        //Stan
        entity.setChanges(Integer.valueOf(entityArray[8]));        //Zmiana
        return entity;
    }
}