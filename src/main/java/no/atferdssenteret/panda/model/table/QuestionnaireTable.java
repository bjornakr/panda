package no.atferdssenteret.panda.model.table;

import no.atferdssenteret.panda.model.Model;
import no.atferdssenteret.panda.model.Questionnaire;
import no.atferdssenteret.panda.view.AbstractOverviewTableModel;

public class QuestionnaireTable extends AbstractOverviewTableModel {
    private static final long serialVersionUID = 1L;

    @Override
    protected String[] headerColumns() {
	String[] headerColumns = {"Spørreskjema", "Status"};
	return headerColumns;
    }

    @Override
    protected ColumnSizes[] columnSizes() {
	ColumnSizes[] columnSizes = {ColumnSizes.WIDE, ColumnSizes.SMALL};
	return columnSizes;
    }

    @Override
    protected Object[] dataColumns(Model model) {
	Questionnaire questionnaire = (Questionnaire)model;
	Object[] columns = new Object[3];
	columns[0] = questionnaire.getName();
	columns[1] = questionnaire.getStatus();
	return columns;
    }
    
}
