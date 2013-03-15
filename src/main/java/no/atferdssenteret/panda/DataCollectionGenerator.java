package no.atferdssenteret.panda;

import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.Target;

public interface DataCollectionGenerator {
	public boolean isApplicable(Target target);
	public DataCollection createDataCollection(Target target);
	public String dataCollectionType();
}
