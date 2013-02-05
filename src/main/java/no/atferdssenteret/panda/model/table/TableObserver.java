package no.atferdssenteret.panda.model.table;


public interface TableObserver {
	public enum TableAction {TARGET_ID_SELECTED, TARGET_CHOSEN}

	public void tableActionPerformed(TableAction tableAction, long targetId);
	
}
