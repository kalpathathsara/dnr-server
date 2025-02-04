package bit.project.server.seed;

import bit.project.server.util.seed.AbstractSeedClass;

public class CampaignstatusData extends AbstractSeedClass {

    public CampaignstatusData(){
        addIdNameData(1, "Ongoing");
        addIdNameData(2, "Completed");
        addIdNameData(3, "To be Started");
    }
}
