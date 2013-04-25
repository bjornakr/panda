package no.atferdssenteret.panda;

import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.Target;

public interface DataCollectionGenerator<T extends Target> {
	public boolean isApplicable(T target);
	public DataCollection createDataCollection(T target);
	public String dataCollectionType();
}
