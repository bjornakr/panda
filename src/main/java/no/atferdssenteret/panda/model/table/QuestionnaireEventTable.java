package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.QuestionnaireEvent;
import no.atferdssenteret.panda.view.AbstractOverviewTableModel;

public class QuestionnaireEventTable extends AbstractOverviewTableModel {
    private static final long serialVersionUID = 1L;

    @Override
    protected String[] headerColumns() {
	String[] headerColumns = {"Dato", "Hendelse", "Kommentar", "Signatur"};
	return headerColumns;
    }

    @Override
    protected Object[] dataColumns(Model model) {
	QuestionnaireEvent questionnaireEvent = (QuestionnaireEvent)model;
	Object[] dataColumns = {questionnaireEvent.getDate(), questionnaireEvent.getType(), questionnaireEvent.getComment(), null};
	return dataColumns;
    }

    @Override
    protected ColumnSizes[] columnSizes() {
	ColumnSizes[] columnSizes = {ColumnSizes.SMALL, ColumnSizes.WIDE, ColumnSizes.WIDE, ColumnSizes.NORMAL};  
	return columnSizes;
    }
}
