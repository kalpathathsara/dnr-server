package bit.project.server.seed;

import bit.project.server.util.seed.AbstractSeedClass;

public class ItemstatusData extends AbstractSeedClass {

    public ItemstatusData(){
        addIdNameData(1, "Available");
        addIdNameData(2, "Occupied");
        addIdNameData(3, "Suspended");
    }
}
