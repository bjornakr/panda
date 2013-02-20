package no.atferdssenteret.panda.fft;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import no.atferdssenteret.panda.controller.table.AbstractTableController;
import no.atferdssenteret.panda.controller.table.AbstractTabsAndTablesController;
import no.atferdssenteret.panda.controller.table.DataCollectionTableController;
import no.atferdssenteret.panda.controller.table.ParticipantTableController;
import no.atferdssenteret.panda.controller.table.QuestionnaireTableController;
import no.atferdssenteret.panda.controller.table.UserTableController;
import no.atferdssenteret.panda.fft.config.DataCollectionRulesSetup;
import no.atferdssenteret.panda.fft.config.QuestionnairesForDataCollectionSetup;
import no.atferdssenteret.panda.model.Session;
import no.atferdssenteret.panda.model.entity.Target;
import no.atferdssenteret.panda.model.table.TableObserver;
import no.atferdssenteret.panda.util.JPATransactor;
import no.atferdssenteret.panda.util.StringUtil;
import no.atferdssenteret.panda.view.ErrorMessageDialog;
import no.atferdssenteret.panda.view.MainWindow;
import no.atferdssenteret.panda.view.TabsAndTablesPanel;
import no.atferdssenteret.panda.view.util.ButtonUtil;
import no.atferdssenteret.panda.view.util.GuiUtil;
import no.atferdssenteret.panda.view.util.TabButton;

public class MainController extends AbstractTabsAndTablesController implements ActionListener, TableObserver {
	public static final String APP_NAME = "PANDA";
	public static final String VERSION = "0.314";
	private List<AbstractTableController> tableControllers;
	private MainWindow view;
	private TabsAndTablesPanel tabsAndTablesPanel;

	public MainController() {
		tabsAndTablesPanel = new TabsAndTablesPanel(this, tableControllers());
		view = new MainWindow(this, tabsAndTablesPanel);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				view.display();
			}
		});
		tableControllers.get(0).updateTableModel();
	}

	@Override
	public Component view() {
		return view;
	}

	@Override
	public List<AbstractTableController> tableControllers() {
		if (tableControllers == null) {
			tableControllers = new LinkedList<AbstractTableController>();
			if (Session.currentSession.user().hasAccessToRestrictedFields()) {
				tableControllers.add(new YouthTableController(this));
				tableControllers.add(new ParticipantTableController(null));
				tableControllers.add(new DataCollectionTableController(null));
				tableControllers.add(new QuestionnaireTableController(null));
				tableControllers.add(new UserTableController());
			}
			else {
				tableControllers.add(new DataCollectionTableController(null));
				tableControllers.add(new YouthTableController(this));				
			}

			for (AbstractTableController tableController : tableControllers) {
				tableController.addTableObserver(this);
			}
		}
		return tableControllers;
	}

	public void addYouthTab(Youth youth) {
		YouthFocusController youthOverviewController = new YouthFocusController(view, youth);
		view.addClosableTab(youthOverviewController.title(), youthOverviewController.view());
	}

	public String title() {
		return APP_NAME + " " + VERSION;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(ButtonUtil.COMMAND_GOTO)) {
			String id = view.targetIdFromGotoField();
			if (StringUtil.validNumber(id) && Target.targetIdExists(Long.parseLong(id))) {
				Youth youth = JPATransactor.getInstance().entityManager().find(Youth.class,
						Long.parseLong(StringUtil.groomString(view.targetIdFromGotoField())));
				addYouthTab(youth);
			}
			else {
				new ErrorMessageDialog("Ugyldig id: " + view.targetIdFromGotoField(), null, view);
			}
		}
		if (event.getSource() instanceof TabButton) {
			JTabbedPane tabbedPane = tabsAndTablesPanel.tabbedPane();
			TabButton tabButton = (TabButton)event.getSource();
			int i = tabbedPane.indexOfTabComponent(tabButton.getButtonTabComponent());

//			if (i == currentSelectedTab) {
//				if (previousSelectedTab > tabbedPane.getTabCount() -1) {
//					previousSelectedTab = tabbedPane.getTabCount() -1;
//				}
//				tabbedPane.setSelectedIndex(previousSelectedTab);
//			}

			if (i != -1) {
				tabbedPane.remove(i);
			}
		}
	}

	public static void main(String[] args) {
		QuestionnairesForDataCollectionSetup.setup();
		DataCollectionRulesSetup.setup();
		Session.createTestSession();
		new MainController();
	}

	@Override
	public void tableActionPerformed(TableAction tableAction, long targetId) {
		if (tableAction == TableAction.TARGET_ID_SELECTED) {
			view.setTargetGotoField(GuiUtil.formatId(targetId));

		}
		else if (tableAction == TableAction.TARGET_CHOSEN) {
			addYouthTab(JPATransactor.getInstance().entityManager().find(Youth.class, targetId));
		}
	}
}
