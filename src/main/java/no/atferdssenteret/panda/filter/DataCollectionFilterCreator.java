package no.atferdssenteret.panda.filter;

import java.util.Calendar;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.model.DataCollectionTypes;
import no.atferdssenteret.panda.model.entity.DataCollection;
import no.atferdssenteret.panda.model.entity.User;
import no.atferdssenteret.panda.model.entity.DataCollection_;
import no.atferdssenteret.panda.util.DateUtil;
import no.atferdssenteret.panda.util.JPATransactor;


public class DataCollectionFilterCreator implements FilterCreator {
	public static final String FILTER_NAME_DATA_COLLECTOR = "Datainnsamler";
	public static final String FILTER_NAME_STATUS = "Status";
	
	public enum Statuses {
		FORTHCOMING_AND_DELAYED("Aktuelle og forsinkede"),
		CONCLUDED("Avklart");
		
		private String name;

		Statuses(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}
	
	private static CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().criteriaBuilder();
	
	public Filter[] createFilters() {
		Filter[] filters = new Filter[4];
		filters[0] = new Filter(FILTER_NAME_STATUS, DataCollectionFilterCreator.Statuses.values());
		filters[1] = new Filter("Type", DataCollectionTypes.getInstance().toArray());
		filters[2] = new Filter("Framdrift", DataCollection.ProgressStatuses.values());
		filters[3] = new Filter(FILTER_NAME_DATA_COLLECTOR, User.dataCollectors().toArray());
		return filters;
	}

	public static Predicate createPredicate(Object value, Root<DataCollection> root) {
		if (value instanceof DataCollectionFilterCreator.Statuses) {
			return createStatusPredicate((DataCollectionFilterCreator.Statuses)value, root);
		}
		else if (DataCollectionTypes.getInstance().contains(value)) {
			return criteriaBuilder.equal(root.get(DataCollection_.type), value);
		}
		else if (value instanceof DataCollection.ProgressStatuses) {
			return criteriaBuilder.equal(root.get(DataCollection_.progressStatus), value);
		}
		else if (value instanceof User) {
			return criteriaBuilder.equal(root.get(DataCollection_.dataCollector), value);
		}
		else {
			return criteriaBuilder.conjunction();
		}
	}

	private static Predicate createStatusPredicate(DataCollectionFilterCreator.Statuses value, Root<DataCollection> root) {
		if (value == DataCollectionFilterCreator.Statuses.CONCLUDED) {
			return createConcludedPredicate(root);
		}
		else if (value == DataCollectionFilterCreator.Statuses.FORTHCOMING_AND_DELAYED) {
			return createForthcomingAndDelayedPredicate(root);
		}
		throw new IllegalArgumentException();
	}

	private static Predicate createConcludedPredicate(Root<DataCollection> root) {
		Predicate givenUpPredicate = criteriaBuilder.equal(root.get(DataCollection_.progressStatus), DataCollection.ProgressStatuses.GIVEN_UP);
		Predicate concludedPredicate = criteriaBuilder.equal(root.get(DataCollection_.progressStatus), DataCollection.ProgressStatuses.COMPLETED);
		return criteriaBuilder.or(givenUpPredicate, concludedPredicate);
	}

	private static Predicate createForthcomingAndDelayedPredicate(Root<DataCollection> root) {
		Predicate isWithinTimeFrame = criteriaBuilder.lessThanOrEqualTo(root.get(DataCollection_.targetDate),
				DateUtil.addTime(DateUtil.today(), Calendar.MONTH, 1));
		Predicate notConcluded = criteriaBuilder.not(createConcludedPredicate(root));
		return criteriaBuilder.and(isWithinTimeFrame, notConcluded);
	}
}
