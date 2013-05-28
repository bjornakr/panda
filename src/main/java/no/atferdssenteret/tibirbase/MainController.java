package no.atferdssenteret.tibirbase;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Persistence;
import javax.swing.SwingUtilities;

import no.atferdssenteret.panda.controller.LoginController;
import no.atferdssenteret.panda.controller.table.AbstractTableController;
import no.atferdssenteret.panda.controller.table.DataCollectionTableController;
import no.atferdssenteret.panda.controller.table.ParticipantTableController;
import no.atferdssenteret.panda.controller.table.QuestionnaireTableController;
import no.atferdssenteret.panda.controller.table.StandardController;
import no.atferdssenteret.panda.controller.table.UserTableController;
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

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class MainController implements StandardController, ActionListener, TableObserver {
	public static final String APP_NAME = "FFTbase";
	public static final double APP_VERSION = 0.314;
	private List<AbstractTableController> tableControllers;
	private MainWindow view;
	private TabsAndTablesPanel tabsAndTablesPanel;

	public MainController() {
//		view = new MainWindow(this, new ImageIcon(getClass().getResource("/fft-logo.png")));
		view = new MainWindow(this, null);
//		view = new MainWindow(this, new ImageIcon("fft-logo.png"));

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				startUp();
			}
		});
	}
	
	private void startUp() {
		view.display();
		try {
			connectToDatabase();
		}
		catch (Exception e) {
			new ErrorMessageDialog("En feil har oppstått ved tilkobling til databasen.", e, view);
			e.printStackTrace();
			System.exit(0);
		}
		if (Session.currentSession == null) {
			new LoginController(view, APP_VERSION);
		}
		tabsAndTablesPanel = new TabsAndTablesPanel(createTableControllers());
		view.addTabsAndTablesPanel(tabsAndTablesPanel);
		tableControllers.get(0).updateTableModel();
	}
	
	private void connectToDatabase() {
//		new JPATransactor(Persistence.createEntityManagerFactory("tibirbase", connectionProperties()));
		new JPATransactor(Persistence.createEntityManagerFactory("panda", connectionProperties()));
		JPATransactor.getInstance().entityManager().isOpen(); // Throws an exception if connection went wrong
	}
	
	private Map<String, String> connectionProperties() {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(System.getenv("PDK"));
		Map<String, String> dbProps = new HashMap<String, String>();
//		dbProps.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/panda");
//		dbProps.put("javax.persistence.jdbc.user", "pandaboy");
//		dbProps.put("javax.persistence.jdbc.password", "panda");
		dbProps.put("javax.persistence.jdbc.url", "jdbc:mysql://atferd56.uio.no/panda");
		dbProps.put("javax.persistence.jdbc.user", "pandabear");
		dbProps.put("javax.persistence.jdbc.password", encryptor.decrypt("pzg5gtGA/XTVBs7dYvV45w=="));
//		dbProps.put("javax.persistence.jdbc.password", "bamboo");
		return dbProps;
	}

	@Override
	public Component view() {
		return view;
	}

	public List<AbstractTableController> createTableControllers() {
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
		return APP_NAME + " " + APP_VERSION;
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
	
	public static void main(String[] args) {
		QDC2.setup();
		new DCG2();
		Session.createTestSession();
		new MainController();
	}
}
