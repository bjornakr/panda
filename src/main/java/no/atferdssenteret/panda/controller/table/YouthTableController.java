package no.atferdssenteret.panda.controller.table;

import java.awt.event.ActionEvent;

import no.atferdssenteret.panda.DataCollectionManager;
import no.atferdssenteret.panda.controller.MainController;
import no.atferdssenteret.panda.controller.YouthController;
import no.atferdssenteret.panda.filter.YouthFilterCreator;
import no.atferdssenteret.panda.model.Target;
import no.atferdssenteret.panda.model.fft.Youth;
import no.atferdssenteret.panda.model.table.YouthTable;
import no.atferdssenteret.panda.view.DefaultAbstractTableModel;
import no.atferdssenteret.panda.view.DefaultTablePanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;

public class YouthTableController extends AbstractTableController {
	private MainController mainController;
	private DefaultTablePanel view;
	private YouthTable tableModel = new YouthTable();

	public YouthTableController(MainController mainController) {
		super("Targets");
		this.mainController = mainController;
		view = new DefaultTablePanel(this, new YouthFilterCreator());
	}

	@Override
	public DefaultTablePanel view() {
		return view;
	}

	@Override
	public DefaultAbstractTableModel tableModel() {
		return tableModel;
	}

	@Override
	protected String getWarningBeforeDelete() {
		return null;
	}

//	public List<Youth> allModels() {
//		TypedQuery<Youth> query = JPATransactor.getInstance().entityManager().createQuery("SELECT y FROM Youth y", Youth.class);
//		return query.getResultList();
//
//		//	List<Youth> models = new LinkedList<Youth>();
//		//	for (Model model : tableModel.allModels()) {
//		//	    models.add((Youth)model);
//		//	}
//		//	return models;
//	}

//	@Override
//	protected List<? extends Model> retrieveModelsForCurrentConditions() {
//		return allModels();
//	}
	
//	@Override
//	protected List<? extends Model> retrieve(Predicate[] predicates) {
//		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();
//		CriteriaQuery<Youth> criteriaQuery = criteriaBuilder.createQuery(Youth.class);
//		criteriaQuery.where(predicates);
//		return JPATransactor.getInstance().entityManager().createQuery(criteriaQuery).getResultList();
//	}

	@Override
	public void evaluateActionEvent(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_CREATE)) {
			YouthController youthController = new YouthController(view.getWindow(), null);
			if (youthController.model() != null) {
//				tableModel.addRow(youthController.model());
				updateTableModel();
			}
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_EDIT)) {
			YouthController youthController = new YouthController(view.getWindow(), (Youth)modelForSelectedTableRow());
			Target target = (Target)youthController.model();
			DataCollectionManager.getInstance().notifyTargetUpdated(target);
			updateTableModel();
		}
		else if (event.getActionCommand().equals(ButtonUtil.COMMAND_DOUBLE_CLICK)) {
			mainController.addYouthTab((Youth)modelForSelectedTableRow());
			//	    new YouthOverviewController(view().getWindow(), (Youth)modelForSelectedTableRow());
		}
	}

	@Override
	protected Class<Youth> getModelClass() {
		return Youth.class;
	}
}
